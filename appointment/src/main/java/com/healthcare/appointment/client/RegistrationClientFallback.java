package com.healthcare.appointment.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.healthcare.appointment.Exception.ResourceNotFoundException;
import com.healthcare.appointment.dto.PatientDetails;

@Component
public class RegistrationClientFallback implements RegistrationClient {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationClientFallback.class);

	@Override
	public PatientDetails getPatientDetails(Long id) {
		logger.info("Circuit Pattern activated");
		throw new ResourceNotFoundException("Patient Service is down at present,please try after sometime");
	}
}