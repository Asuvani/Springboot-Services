package com.healthcare.authservice.config;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;

@Configuration
public class JwtKeyConfig {

	/*@Bean
	public KeyPair keyPair() throws Exception {

		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

		generator.initialize(2048);

		return generator.generateKeyPair();
	}

	@Bean
	public JwtEncoder jwtEncoder(KeyPair keyPair) {

	    RSAKey rsaKey = new RSAKey.Builder(
	            (RSAPublicKey) keyPair.getPublic())
	            .privateKey((RSAPrivateKey) keyPair.getPrivate())
	            .keyID("auth-key-1")
	            .build();

	    JWKSet jwkSet = new JWKSet(rsaKey);

	    JWKSource<SecurityContext> jwkSource =
	            new ImmutableJWKSet<SecurityContext>(jwkSet);

	    return new NimbusJwtEncoder(jwkSource);
	    
	    
	}*/
	
	
	
	@Bean
	public JwtEncoder jwtEncoder(
	        @Value("${spring.security.oauth2.resourceserver.jwt.public-key-location}") Resource publicResource,
	        @Value("${spring.security.oauth2.resourceserver.jwt.private-key-location}") Resource privateResource) throws Exception {
	    
	    RSAPrivateKey privateKey;
	    try (InputStream in = privateResource.getInputStream()) {
	        privateKey = RsaKeyConverters.pkcs8().convert(in);
	    }

	    RSAPublicKey publicKey;
	    try (InputStream in = publicResource.getInputStream()) {
	        publicKey = RsaKeyConverters.x509().convert(in);
	    }

	    RSAKey rsaKey = new RSAKey.Builder(publicKey)
	            .privateKey(privateKey)
	            .keyID("auth-key-1")
	            .build();

	    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));

	    // Added 'new' keyword here
	    return new NimbusJwtEncoder(jwkSource);
	}
	
	
}
