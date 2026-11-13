package com.healthcare.appointment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.appointment.dto.AppointmentRequest;
import com.healthcare.appointment.entity.Appointment;
import com.healthcare.appointment.facade.AppointmentFacade;
import com.healthcare.appointment.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	 private final AppointmentService service;	
	
	 private final AppointmentFacade appointmentFacade;
	 
	 public AppointmentController(AppointmentFacade appointmentFacade,AppointmentService service) { 
		 this.appointmentFacade = appointmentFacade; 
		 this.service=service;
		 }

	@PostMapping("/book")
	public ResponseEntity<Appointment> book(@RequestBody AppointmentRequest request) {
		logger.info("Start the Appointment booking");
		Appointment appointment = appointmentFacade.bookAppointmentFacade(request);
		logger.info("End the Appointment booking");
		return ResponseEntity.ok(appointment);
	}

	@GetMapping("/{appointmentId}")
	public ResponseEntity<Appointment> getById(@PathVariable Long id) {
		logger.info("Fetch the Appointment booking Details");
		return ResponseEntity.of(service.getAppointment(id));
	}
}
