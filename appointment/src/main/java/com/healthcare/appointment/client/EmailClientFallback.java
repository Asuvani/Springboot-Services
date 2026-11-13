package com.healthcare.appointment.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.healthcare.appointment.Exception.ResourceNotFoundException;
import com.healthcare.appointment.dto.EmailDetails;

@Component
public class EmailClientFallback implements EmailClient {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailClientFallback.class);

	

	@Override
	public String sendEmail(EmailDetails emailDetails) {
		logger.info("Circuit Pattern activated");
		throw new ResourceNotFoundException("Email Service is down at present,please try after sometime");
	}
}