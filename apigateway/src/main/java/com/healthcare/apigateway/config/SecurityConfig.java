package com.healthcare.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

   /* @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // Public endpoints
                .requestMatchers(
                    "/auth/**",
                    "/actuator/health"
                ).permitAll()

                // Protected endpoints
                .requestMatchers("/patients/**")
                    .authenticated()

                .requestMatchers("/appointments/**")
                    .authenticated()
                    
                .requestMatchers("/email/**")
                .authenticated()

                // Everything else
                .anyRequest().authenticated()
            )

            // JWT validation
            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(Customizer.withDefaults())
            );

        return http.build();
    }*/
	
	/*@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**").permitAll()
                        
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }*/
	
	@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            // Disable CSRF since stateless JWTs are used
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            
            // Define route permissions
            .authorizeExchange(exchanges -> exchanges
                // Allow unauthenticated access to auth endpoints
                .pathMatchers("/auth/**", "/actuator/health").permitAll()
                // Require valid JWT for all other requests
                .anyExchange().authenticated()
            )
            
            // Enable JWT validation using your ReactiveJwtDecoder bean
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            );

        return http.build();
    }
}
