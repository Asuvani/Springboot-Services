package com.healthcare.appointment.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.healthcare.appointment.Exception.ResourceNotFoundException;
import com.healthcare.appointment.entity.Provider;
import com.healthcare.appointment.repository.ProviderRepository;

@Service
public class ProviderService {
	
	private final ProviderRepository providerRepository;
	public ProviderService(ProviderRepository providerRepository){
		this.providerRepository=providerRepository;
	}
	
	public Provider isProviderExist(Long id) {
		Optional<Provider> provider = providerRepository.findById(id);
		if (provider.isEmpty()) {
			throw new ResourceNotFoundException("Provider is not available");
		}
		
		return provider.get();
	}

}
