package com.healthcare.apigateway.config;

import java.io.InputStream;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
public class JwtConfig {

    @Bean
    public ReactiveJwtDecoder jwtDecoder(
            @Value("${spring.security.oauth2.resourceserver.jwt.public-key-location}")
            Resource resource) throws Exception {

        RSAPublicKey publicKey;

        try (InputStream input = resource.getInputStream()) {
            publicKey = (RSAPublicKey)
                    RsaKeyConverters.x509().convert(input);
        }

        return NimbusReactiveJwtDecoder
                .withPublicKey(publicKey)
                .build();
    }
}
