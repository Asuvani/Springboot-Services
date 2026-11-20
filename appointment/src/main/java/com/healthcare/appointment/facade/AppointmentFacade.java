package com.healthcare.appointment.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.healthcare.appointment.Exception.ResourceNotFoundException;
import com.healthcare.appointment.client.EmailClient;
import com.healthcare.appointment.client.RegistrationClient;
import com.healthcare.appointment.controller.AppointmentController;
import com.healthcare.appointment.dto.AppointmentRequest;
import com.healthcare.appointment.dto.EmailDetails;
import com.healthcare.appointment.dto.PatientDetails;
import com.healthcare.appointment.entity.Appointment;
import com.healthcare.appointment.entity.Provider;
import com.healthcare.appointment.service.AppointmentService;
import com.healthcare.appointment.service.ProviderService;

@Component
public class AppointmentFacade {
	
	private static final Logger logger = LoggerFactory.getLogger(AppointmentFacade.class);
	
	private final AppointmentService appointmentService;
	private final ProviderService providerService;
	private final RegistrationClient registrationClient;
	private final EmailClient emailClient;

	public AppointmentFacade(AppointmentService appointmentService, ProviderService providerService,
			RegistrationClient registrationClient,EmailClient emailClient) {
		this.appointmentService = appointmentService;
		this.providerService = providerService;
		this.registrationClient = registrationClient;
		this.emailClient=emailClient;
	}
	
	public Appointment bookAppointmentFacade(AppointmentRequest request) {
		logger.info("Start the bookAppointmentFacade ");
		PatientDetails patientDetails = registrationClient.getPatientDetails(request.getPatientId());
		logger.info("Fetch the patientDetails "+patientDetails.getId());
		if (patientDetails == null || patientDetails.getId() == null) {
			throw new ResourceNotFoundException("Patient is not registered or inactive");
		}
		
		Provider provider=providerService.isProviderExist(request.getProviderId());
		
		Appointment saved=appointmentService.bookAppointment(request, provider);
		
		sendEmail(patientDetails.getEmail());
		
		return saved;
	}
	
	private void sendEmail(String email) {
		EmailDetails emailDetail = new EmailDetails();
		emailDetail.setMsgBody("Successfully fixed your appointment with Consultant");
		emailDetail.setRecipient(email);
		emailDetail.setSubject("Appointment Email");
		emailClient.sendEmail(emailDetail);
	}

}
