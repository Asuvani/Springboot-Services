package com.healthcare.registration.controller;

import com.healthcare.registration.Exception.ResourceNotFoundException;
import com.healthcare.registration.dto.PatientRegistrationReponse;
import com.healthcare.registration.dto.PatientRegistrationRequest;
import com.healthcare.registration.entity.PatientDetails;
import com.healthcare.registration.facade.PatientServiceFacade;
import com.healthcare.registration.service.PatientService;

import io.micrometer.tracing.Tracer;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
	
    private final PatientService patientService;
    
    private final PatientServiceFacade patientServiceFacade;  
    

    public PatientController(PatientService patientService,PatientServiceFacade patientServiceFacade) {
        this.patientService = patientService;
        this.patientServiceFacade = patientServiceFacade;
    }

    @PostMapping("/registration")
    public ResponseEntity<PatientRegistrationReponse> registerPatient(
            @Valid @RequestBody PatientRegistrationRequest request) {
    	logger.info("Start Register Patient details ");
    	PatientRegistrationReponse response = patientServiceFacade.registerPatient(request);
    	logger.info("End Register Patient details");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")    
    public ResponseEntity<PatientDetails> getPatient(@PathVariable Long id) {
    	logger.info("Fetch tPatient details "+id);
    	return patientService.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Patient Details not found with id: " + id));
        
    }
    
    
}