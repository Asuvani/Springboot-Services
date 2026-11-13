package com.healthcare.appointment.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.appointment.dto.AppointmentRequest;
import com.healthcare.appointment.entity.Appointment;
import com.healthcare.appointment.entity.Provider;
import com.healthcare.appointment.repository.AppointmentRepository;


@Service
public class AppointmentService {

	private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

	private final AppointmentRepository appointmentRepository;

	public AppointmentService(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Transactional(rollbackFor =Exception.class)
	public Appointment bookAppointment(AppointmentRequest request,Provider provider) {
		logger.info("Start bookAppointment");
		try {

			Appointment appointment = new Appointment();
			appointment.setPatientId(request.getPatientId());
			appointment.setProvider(provider);
			appointment.setAvailabilityId(request.getAvailabilityId());
			appointment.setAppointmentDate(request.getAppointmentDate());
			appointment.setStartTime(request.getStartTime());
			appointment.setEndTime(request.getEndTime());
			appointment.setStatus("CONFIRMED");
			appointment.setCreatedAt(LocalDateTime.now());

			Appointment saved= appointmentRepository.save(appointment);			
			
			return saved;

		} catch (Exception exception) {
			throw new RuntimeException("Appointment booking failed", exception);
		}

	}
	
	

	@Transactional(readOnly = true)
	public Optional<Appointment> getAppointment(Long id) {

		return appointmentRepository.findById(id);
	}
}