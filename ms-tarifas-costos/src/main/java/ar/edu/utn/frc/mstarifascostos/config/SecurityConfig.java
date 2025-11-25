package ar.edu.utn.frc.mstarifascostos.config;

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
                                "/actuator/health",
                                "/actuator/info",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Logs y otras herramientas internas: solo ADMIN
                        .requestMatchers("/logs/**").hasRole("ADMIN")

                        // Consultas de tarifas/costos: CLIENTE o ADMIN pueden hacer GET
                        .requestMatchers(HttpMethod.GET,
                                "/tarifas/**",
                                "/costos/**",
                                "/tarifas-costos/**"
                        ).hasAnyRole("CLIENTE", "ADMIN")

                        // Crear / modificar / borrar tarifas/costos: solo ADMIN
                        .requestMatchers(
                                "/tarifas/**",
                                "/costos/**",
                                "/tarifas-costos/**"
                        ).hasRole("ADMIN")

                        // Cualquier otra cosa: requiere estar autenticado
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    /**
     * Convierte los roles de Keycloak (realm_access.roles)
     * en authorities de Spring Security con prefijo "ROLE_".
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
