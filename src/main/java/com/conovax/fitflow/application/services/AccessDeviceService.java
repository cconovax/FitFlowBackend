package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.AccessDeviceRequest;
import com.conovax.sexbody.application.dto.response.AccessDeviceResponse;
import com.conovax.sexbody.domain.entities.AccessDevice;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.AccessDeviceRepository;
import com.conovax.sexbody.domain.repositories.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessDeviceService {

	private final AccessDeviceRepository repository;
	private final GymRepository gymRepository;

	@Transactional(readOnly = true)
	public List<AccessDeviceResponse> getAllByGymId(Long gymId) {
		ensureGymExists(gymId);
		return repository.findAllByGymId(gymId).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public AccessDeviceResponse getById(Long gymId, Long deviceId) {
		AccessDevice device = repository.findByIdAndGymId(deviceId, gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Dispositivo no encontrado con ID: " + deviceId));
		return toResponse(device);
	}

	@Transactional
	public AccessDeviceResponse create(Long gymId, AccessDeviceRequest request) {
		ensureGymExists(gymId);
		AccessDevice device = AccessDevice.builder()
				.gymId(gymId)
				.name(request.name().trim())
				.deviceType(request.deviceType().trim())
				.serialNumber(request.serialNumber())
				.macAddress(request.macAddress())
				.location(request.location())
				.status(request.status() == null ? Boolean.TRUE : request.status())
				.build();
		return toResponse(repository.save(device));
	}

	@Transactional
	public AccessDeviceResponse update(Long gymId, Long deviceId, AccessDeviceRequest request) {
		AccessDevice existing = repository.findByIdAndGymId(deviceId, gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Dispositivo no encontrado con ID: " + deviceId));

		AccessDevice updated = existing.toBuilder()
				.name(request.name().trim())
				.deviceType(request.deviceType().trim())
				.serialNumber(request.serialNumber())
				.macAddress(request.macAddress())
				.location(request.location())
				.status(request.status() == null ? existing.getStatus() : request.status())
				.build();

		return toResponse(repository.save(updated));
	}

	@Transactional
	public void delete(Long gymId, Long deviceId) {
		AccessDevice device = repository.findByIdAndGymId(deviceId, gymId)
				.orElseThrow(() -> new ResourceNotFoundException("Dispositivo no encontrado con ID: " + deviceId));
		repository.deleteById(device.getId());
	}

	private void ensureGymExists(Long gymId) {
		if (!gymRepository.existsById(gymId)) {
			throw new ResourceNotFoundException("Gym no encontrado con ID: " + gymId);
		}
	}

	private AccessDeviceResponse toResponse(AccessDevice device) {
		return new AccessDeviceResponse(
				device.getId(),
				device.getGymId(),
				device.getName(),
				device.getDeviceType(),
				device.getSerialNumber(),
				device.getMacAddress(),
				device.getLocation(),
				device.getStatus(),
				device.getCreatedAt(),
				device.getUpdatedAt()
		);
	}
}
