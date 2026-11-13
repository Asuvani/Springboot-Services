package com.healthcare.appointment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.healthcare.appointment.dto.PatientDetails;

@FeignClient(name = "registration-service", fallback = RegistrationClientFallback.class)
public interface RegistrationClient {

	@GetMapping("/patients/{id}")
	PatientDetails getPatientDetails(@PathVariable Long id);
}
