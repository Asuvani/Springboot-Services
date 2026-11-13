package com.healthcare.appointment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.healthcare.appointment.dto.EmailDetails;


@FeignClient(name = "email-service", fallback = EmailClientFallback.class)
public interface EmailClient {

	@PostMapping("/email/sendEmail")
	String sendEmail(@RequestBody EmailDetails emailDetails);
}
