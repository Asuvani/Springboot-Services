package com.healthcare.appointment.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "provider_availability")
@Getter
@Setter
public class ProviderAvailability {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long availabilityId;
	private Long providerId;
    private LocalDate availableDate;
    private LocalTime start_time;
    private LocalTime end_time;
    private String slotStatus;

}
