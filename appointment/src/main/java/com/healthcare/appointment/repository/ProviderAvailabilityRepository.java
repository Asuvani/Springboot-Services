package com.healthcare.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.healthcare.appointment.entity.ProviderAvailability;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProviderAvailabilityRepository extends JpaRepository<ProviderAvailability, Long> {

	@Modifying
	@Transactional
	@Query(value = """
			UPDATE provider_availability
			SET slot_status = 'BOOKED'
			WHERE availability_id = :availabilityId
			AND provider_id = :providerId
			AND slot_status = 'AVAILABLE'
			""", nativeQuery = true)
	int reserveSlot(@Param("availabilityId") Long availabilityId, @Param("providerId") Long providerId);

	@Modifying
	@Transactional
	@Query(value = """
			UPDATE provider_availability
			SET slot_status = 'AVAILABLE'
			WHERE availability_id = :availabilityId
			AND provider_id = :providerId
			AND slot_status = 'BOOKED'
			""", nativeQuery = true)
	int releaseSlot(@Param("availabilityId") Long availabilityId, @Param("providerId") Long providerId);
}
