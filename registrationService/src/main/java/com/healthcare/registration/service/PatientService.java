package com.healthcare.registration.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.registration.dto.PatientRegistrationReponse;
import com.healthcare.registration.dto.PatientRegistrationRequest;
import com.healthcare.registration.entity.PatientDetails;
import com.healthcare.registration.repository.PatientRepository;

@Service
public class PatientService {

	private final PatientRepository patientRepository;

	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Transactional
	public PatientRegistrationReponse registerPatient(PatientRegistrationRequest request) {
		if (patientRepository.existsByEmail(request.email())) {
			throw new IllegalArgumentException("A Patient Details with this email already exists");
		}

		PatientDetails patient=new PatientDetails();
		patient.setFirstName(request.firstName());
		patient.setLastName(request.lastName());
		patient.setDateOfBirth(request.dateOfBirth());
		patient.setEmail(request.email());
		patient.setPhoneNo(Long.valueOf(request.phone()));
		patient.setInsuranceId(request.insuranceId());
		PatientDetails p1 = patientRepository.save(patient);

		PatientRegistrationReponse reponse = new PatientRegistrationReponse();
		reponse.setFirstName(p1.getFirstName());
		reponse.setLastName(p1.getLastName());
		reponse.setEmail(p1.getEmail());
		reponse.setMessage("A Patient details has been registered successfully");
		
		return reponse;
	}

	@Transactional(readOnly = true)
	public Optional<PatientDetails> findById(Long id) {
		return patientRepository.findById(id);
	}
}