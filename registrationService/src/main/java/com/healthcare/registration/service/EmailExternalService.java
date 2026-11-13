package com.healthcare.registration.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.healthcare.registration.Exception.ResourceNotFoundException;
import com.healthcare.registration.dto.EmailDetails;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EmailExternalService {
	
	private final RestTemplate restTemplate;
	
	@Value("${spring.email.url}")
	private String emailUrl;
	
	public EmailExternalService(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
		
	}
	
	@CircuitBreaker(name="emailExternalService", fallbackMethod ="sendEmailFallback")
	public void sendEmail(String email) {
		EmailDetails emailDetail = new EmailDetails();
		emailDetail.setMsgBody("Successfully created Patient deatils");
		emailDetail.setRecipient(email);
		emailDetail.setSubject("Email");

		restTemplate.postForEntity(emailUrl, emailDetail, String.class);
	}	
	
	public void sendEmailFallback(Exception ex) {
		throw new ResourceNotFoundException("Email service is down");
	}
	

}
