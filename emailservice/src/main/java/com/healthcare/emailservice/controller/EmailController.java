package com.healthcare.emailservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.emailservice.dto.EmailDetails;
import com.healthcare.emailservice.service.EmailService;

 

 

@RestController
@RequestMapping("/email")
public class EmailController {

	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDetails emailDetails) {
    	logger.info("EmailController sendEmail ");

        emailService.sendEmail(emailDetails);
        return ResponseEntity.ok("Email sent successfully");
    }

    
    
    
}