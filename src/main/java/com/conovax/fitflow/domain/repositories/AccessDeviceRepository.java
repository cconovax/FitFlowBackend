package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.AccessDevice;

import java.util.List;
import java.util.Optional;

public interface AccessDeviceRepository {
	AccessDevice save(AccessDevice device);

	List<AccessDevice> findAllByGymId(Long gymId);

	Optional<AccessDevice> findById(Long id);

	Optional<AccessDevice> findByIdAndGymId(Long id, Long gymId);

	boolean existsById(Long id);

	void deleteById(Long id);
}
