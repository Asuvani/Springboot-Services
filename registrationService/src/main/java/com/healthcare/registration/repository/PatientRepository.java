package com.healthcare.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.registration.entity.PatientDetails;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientDetails, Long> {

    boolean existsByEmail(String email);

    Optional<PatientDetails> findByEmail(String email);
}