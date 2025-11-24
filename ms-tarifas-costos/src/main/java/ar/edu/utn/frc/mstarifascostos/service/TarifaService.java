package ar.edu.utn.frc.mstarifascostos.service;

import ar.edu.utn.frc.mstarifascostos.exception.BadRequestException;
import ar.edu.utn.frc.mstarifascostos.exception.ResourceNotFoundException;
import ar.edu.utn.frc.mstarifascostos.model.Tarifa;
import ar.edu.utn.frc.mstarifascostos.model.dto.DistanciaDTO;
import ar.edu.utn.frc.mstarifascostos.model.dto.CalcularTramoRequest;
import ar.edu.utn.frc.mstarifascostos.model.dto.CalcularTramoResponse;
import ar.edu.utn.frc.mstarifascostos.repository.TarifaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepository tarifaRepository;
    private final RutasTramosClient rutasTramosClient;

    // ===================== CRUD =====================

    public List<Tarifa> listar() {
        return tarifaRepository.findAll();
    }

    public Tarifa buscarPorId(Long id) {
        return tarifaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No existe tarifa con id=" + id));
    }

    public Tarifa guardar(Tarifa tarifa) {
        validarTarifa(tarifa);
        return tarifaRepository.save(tarifa);
    }

    /** Actualiza una tarifa existente */
    public Tarifa actualizar(Long id, Tarifa datosActualizados) {
        Tarifa existente = buscarPorId(id); // lanza ResourceNotFound si no existe

        existente.setNombre(datosActualizados.getNombre());
        existente.setRangoPesoMin(datosActualizados.getRangoPesoMin());
        existente.setRangoPesoMax(datosActualizados.getRangoPesoMax());
        existente.setRangoVolumenMin(datosActualizados.getRangoVolumenMin());
        existente.setRangoVolumenMax(datosActualizados.getRangoVolumenMax());
        existente.setCostoKmBase(datosActualizados.getCostoKmBase());
        existente.setCargoGestionPorTramo(datosActualizados.getCargoGestionPorTramo());

        validarTarifa(existente);
        return tarifaRepository.save(existente);
    }

    public void eliminar(Long id) {
        Tarifa existente = buscarPorId(id); // lanza excepción si no está
        tarifaRepository.delete(existente);
    }

    // ===================== REGLAS DE NEGOCIO =====================

    public double calcularCosto(Long tarifaId,
                                double distanciaKm,
                                int cantidadTramos) {

        if (distanciaKm < 0) {
            throw new BadRequestException("La distancia en km no puede ser negativa.");
        }
        if (cantidadTramos <= 0) {
            throw new BadRequestException("La cantidad de tramos debe ser mayor a cero.");
        }

        Tarifa tarifa = buscarPorId(tarifaId);

        BigDecimal distancia = BigDecimal
                .valueOf(distanciaKm)
                .setScale(3, RoundingMode.HALF_UP);

        return calcularCosto(tarifa, distancia, cantidadTramos);
    }

    /** Variante que recibe coordenadas y delega la distancia a ms-rutas-tramos */
    public double calcularCostoConCoordenadas(Long tarifaId,
                                              double origenLat,
                                              double origenLon,
                                              double destinoLat,
                                              double destinoLon,
                                              int cantidadTramos) {

        if (cantidadTramos <= 0) {
            throw new BadRequestException("La cantidad de tramos debe ser mayor a cero.");
        }

        // 1) Pedimos la distancia a ms-rutas-tramos
        DistanciaDTO dto = rutasTramosClient.obtenerDistancia(
                origenLat, origenLon,
                destinoLat, destinoLon
        );

        // 2) metros → km
        BigDecimal distanciaKm = BigDecimal
                .valueOf(dto.getDistanciaMetros())
                .divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);

        // 3) Calculamos el costo reutilizando la misma lógica
        Tarifa tarifa = buscarPorId(tarifaId);
        return calcularCosto(tarifa, distanciaKm, cantidadTramos);
    }


    // ===================== CÁLCULO DE TRAMO  =====================

    public CalcularTramoResponse calcularTramo(CalcularTramoRequest request) {

        if (request == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser nulo.");
        }
        if (request.getOrigen() == null || request.getDestino() == null) {
            throw new BadRequestException("Origen y destino son obligatorios.");
        }
        if (request.getContenedor() == null) {
            throw new BadRequestException("Los datos del contenedor son obligatorios.");
        }
        if (request.getEstadiaDias() < 0) {
            throw new BadRequestException("La cantidad de días de estadía no puede ser negativa.");
        }

        // 1) Peso y volumen del contenedor
        BigDecimal peso = BigDecimal
                .valueOf(request.getContenedor().getPesoKg())
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal volumen = BigDecimal
                .valueOf(request.getContenedor().getVolumenM3())
                .setScale(3, RoundingMode.HALF_UP);

        // 2) Buscar una tarifa cuyo rango contenga ese peso y volumen
        List<Tarifa> tarifas = tarifaRepository.findAll();

        Tarifa tarifa = tarifas.stream()
                .filter(t -> t.getRangoPesoMin() != null
                        && t.getRangoPesoMax() != null
                        && t.getRangoVolumenMin() != null
                        && t.getRangoVolumenMax() != null
                        && peso.compareTo(t.getRangoPesoMin()) >= 0
                        && peso.compareTo(t.getRangoPesoMax()) <= 0
                        && volumen.compareTo(t.getRangoVolumenMin()) >= 0
                        && volumen.compareTo(t.getRangoVolumenMax()) <= 0
                )
                .findFirst()
                .orElseThrow(() -> new BadRequestException(
                        "No existe una tarifa configurada para peso="
                                + peso + " kg y volumen=" + volumen + " m3"
                ));

        // 3) Pedir distancia y duración a ms-rutas-tramos (OSRM)
        DistanciaDTO dto = rutasTramosClient.obtenerDistancia(
                request.getOrigen().getLat(),
                request.getOrigen().getLon(),
                request.getDestino().getLat(),
                request.getDestino().getLon()
        );

        BigDecimal distanciaKm = BigDecimal
                .valueOf(dto.getDistanciaMetros())
                .divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);

        // 4) Costo base usando la lógica que ya tenés (1 tramo)
        double costoBase = calcularCosto(tarifa, distanciaKm, 1);
        BigDecimal costoBaseBD = BigDecimal
                .valueOf(costoBase)
                .setScale(2, RoundingMode.HALF_UP);

        // (Opcional y simple) sumarle algo por estadía y camión — para no complicar:
        BigDecimal extra = BigDecimal.ZERO;

        if (request.getCamion() != null) {
            // Ejemplo MUY simple: costoBaseKm * distancia
            BigDecimal costoCamion = BigDecimal
                    .valueOf(request.getCamion().getCostoBaseKm())
                    .max(BigDecimal.ZERO)
                    .multiply(distanciaKm);
            extra = extra.add(costoCamion);
        }

        if (request.getEstadiaDias() > 0) {
            // Ejemplo: usamos cargoGestionPorTramo * días de estadía
            extra = extra.add(
                    tarifa.getCargoGestionPorTramo()
                            .multiply(BigDecimal.valueOf(request.getEstadiaDias()))
            );
        }

        BigDecimal total = costoBaseBD.add(extra).setScale(2, RoundingMode.HALF_UP);

        // 5) Armamos el DTO de respuesta
        long duracionMin = Math.round(dto.getDuracionSegundos() / 60.0);

        CalcularTramoResponse.TarifaBasicaDTO tarifaDTO =
                new CalcularTramoResponse.TarifaBasicaDTO(tarifa.getId(), tarifa.getNombre());

        CalcularTramoResponse response = new CalcularTramoResponse();
        response.setDistanciaKm(distanciaKm.doubleValue());
        response.setDuracionEstMin(duracionMin);
        response.setTarifaAplicada(tarifaDTO);
        response.setCostoAproximado(total.doubleValue());

        return response;
    }



    // ===================== PRIVADOS =====================

    private double calcularCosto(Tarifa tarifa,
                                 BigDecimal distanciaKm,
                                 int cantidadTramos) {

        if (tarifa.getCostoKmBase() == null || tarifa.getCargoGestionPorTramo() == null) {
            throw new BadRequestException("La tarifa no tiene definidos costoKmBase o cargoGestionPorTramo.");
        }

        BigDecimal costoKmBase = tarifa.getCostoKmBase();
        BigDecimal cargoGestionPorTramo = tarifa.getCargoGestionPorTramo();

        BigDecimal costoDistancia = costoKmBase.multiply(distanciaKm);
        BigDecimal costoTramos = cargoGestionPorTramo.multiply(
                BigDecimal.valueOf(cantidadTramos)
        );

        BigDecimal total = costoDistancia.add(costoTramos);
        return total.doubleValue();
    }

    private void validarTarifa(Tarifa tarifa) {
        if (tarifa.getNombre() == null || tarifa.getNombre().isBlank()) {
            throw new BadRequestException("El nombre de la tarifa es obligatorio.");
        }
        if (tarifa.getRangoPesoMin() == null || tarifa.getRangoPesoMax() == null) {
            throw new BadRequestException("Los rangos de peso son obligatorios.");
        }
        if (tarifa.getRangoVolumenMin() == null || tarifa.getRangoVolumenMax() == null) {
            throw new BadRequestException("Los rangos de volumen son obligatorios.");
        }
        if (tarifa.getCostoKmBase() == null || tarifa.getCostoKmBase().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("El costoKmBase debe ser >= 0.");
        }
        if (tarifa.getCargoGestionPorTramo() == null || tarifa.getCargoGestionPorTramo().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("El cargoGestionPorTramo debe ser >= 0.");
        }
    }
}
