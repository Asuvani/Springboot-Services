package com.healthcare.registration.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.healthcare.registration.dto.PatientRegistrationReponse;
import com.healthcare.registration.dto.PatientRegistrationRequest;
import com.healthcare.registration.service.EmailExternalService;
import com.healthcare.registration.service.PatientService;

@Component
public class PatientServiceFacade {
	
	private static final Logger logger = LoggerFactory.getLogger(PatientServiceFacade.class);
	
	private final PatientService patientService;
	private final EmailExternalService emailExternalService;
	
	
	public PatientServiceFacade(PatientService patientService,EmailExternalService emailExternalService) {
		this.patientService=patientService;
		this.emailExternalService=emailExternalService;
		
	}
	
	public PatientRegistrationReponse registerPatient(PatientRegistrationRequest request) {
		logger.info("Start PatientServiceFacade RegisterPatient");
		PatientRegistrationReponse res=patientService.registerPatient(request);
		logger.info("End PatientServiceFacade RegisterPatient");
		
		if(res!=null && res.getMessage()!=null) {
			emailExternalService.sendEmail(request.email());
		}
		
		return res;
		
	}
	
	
	

}
