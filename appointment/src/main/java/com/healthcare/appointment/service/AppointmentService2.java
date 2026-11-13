package com.healthcare.appointment.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.appointment.Exception.ResourceNotFoundException;
import com.healthcare.appointment.client.EmailClient;
import com.healthcare.appointment.client.RegistrationClient;
import com.healthcare.appointment.dto.AppointmentRequest;
import com.healthcare.appointment.entity.Appointment;
import com.healthcare.appointment.entity.Provider;
import com.healthcare.appointment.repository.AppointmentRepository;
import com.healthcare.appointment.repository.ProviderRepository;
import com.healthcare.appointment.dto.EmailDetails;
import com.healthcare.appointment.dto.PatientDetails;


@Service
public class AppointmentService2 {

	private static final Logger logger = LoggerFactory.getLogger(AppointmentService2.class);

	private final AppointmentRepository appointmentRepository;
	private final ProviderRepository providerRepository;

	private final RegistrationClient registrationClient;
	private final EmailClient emailClient;

	public AppointmentService2(AppointmentRepository appointmentRepository, ProviderRepository providerRepository,
			RegistrationClient registrationClient,EmailClient emailClient) {
		this.appointmentRepository = appointmentRepository;
		this.providerRepository = providerRepository;
		this.registrationClient = registrationClient;
		this.emailClient=emailClient;
	}

	@Transactional(rollbackFor =Exception.class)
	public Appointment bookAppointment(AppointmentRequest request) {

		logger.info("Start bookAppointment");
		PatientDetails patientDetails = registrationClient.getPatientDetails(request.getPatientId());

		if (patientDetails == null || patientDetails.getId() == null) {
			throw new ResourceNotFoundException("Patient is not registered or inactive");
		}

		Optional<Provider> id = providerRepository.findById(request.getProviderId());
		if (id.isEmpty()) {
			throw new ResourceNotFoundException("Provider is not available");
		}

		try {

			Appointment appointment = new Appointment();

			appointment.setPatientId(request.getPatientId());
			appointment.setProvider(id.get());
			appointment.setAvailabilityId(request.getAvailabilityId());
			appointment.setAppointmentDate(request.getAppointmentDate());
			appointment.setStartTime(request.getStartTime());
			appointment.setEndTime(request.getEndTime());
			appointment.setStatus("CONFIRMED");
			appointment.setCreatedAt(LocalDateTime.now());

			Appointment saved= appointmentRepository.save(appointment);
			
			//sendEmail(patientDetails.getEmail());
			
			return saved;

		} catch (Exception exception) {
			throw new RuntimeException("Appointment booking failed", exception);
		}

	}
	
	private void sendEmail(String email) {
		EmailDetails emailDetail = new com.healthcare.appointment.dto.EmailDetails();
		emailDetail.setMsgBody("Successfully fixed your appointment with Consultant");
		emailDetail.setRecipient(email);
		emailDetail.setSubject("Email");
		emailClient.sendEmail(emailDetail);
	}

	@Transactional(readOnly = true)
	public Optional<Appointment> getAppointment(Long id) {

		return appointmentRepository.findById(id);
	}
}