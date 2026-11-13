package com.healthcare.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.healthcare.appointment.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findByPatientId(Long patientId);

	@Query(value = """
			SELECT *
			FROM appointments
			WHERE patient_id = :patientId
			ORDER BY appointment_date DESC
			""", nativeQuery = true)
	List<Appointment> getPatientAppointments(@Param("patientId") Long patientId);
}
