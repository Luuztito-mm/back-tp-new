package ar.edu.utn.frc.msrutastramos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (sin token)
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/actuator/health",
                                "/actuator/info",
                                "/rutas/distancia"
                        ).permitAll()

                        // Logs: solo ADMIN
                        .requestMatchers("/logs/**").hasRole("ADMIN")

                        // Consultas de rutas y tramos:
                        // ADMIN y TRANSPORTISTA pueden hacer GET
                        .requestMatchers(HttpMethod.GET, "/rutas/**", "/tramos/**")
                            .hasAnyRole("ADMIN", "TRANSPORTISTA")

                        // Alta / baja / modificación de rutas y tramos: solo ADMIN
                        .requestMatchers("/rutas/**", "/tramos/**")
                            .hasRole("ADMIN")

                        // Cualquier otra cosa: usuario autenticado (con cualquier rol válido)
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    /**
     * Convierte los roles de Keycloak (realm_access.roles)
     * en authorities de Spring Security con el prefijo "ROLE_".
     *
     * Ejemplo: "ADMIN" -> "ROLE_ADMIN", "CLIENTE" -> "ROLE_CLIENTE".
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<GrantedAuthority> authorities = new ArrayList<>();

            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess != null) {
                Object rolesObj = realmAccess.get("roles");
                if (rolesObj instanceof Collection<?> roles) {
                    for (Object role : roles) {
                        String roleName = role.toString(); // "ADMIN", "CLIENTE", "TRANSPORTISTA", etc.
                        if (!roleName.startsWith("ROLE_")) {
                            roleName = "ROLE_" + roleName;
                        }
                        authorities.add(new SimpleGrantedAuthority(roleName));
                    }
                }
            }

            return authorities;
        });

        return converter;
    }
}
