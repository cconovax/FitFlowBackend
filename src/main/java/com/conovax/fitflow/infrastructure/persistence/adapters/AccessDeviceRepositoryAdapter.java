package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.AccessDevice;
import com.conovax.fitflow.domain.repositories.AccessDeviceRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.GymJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.AccessDeviceMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.AccessDeviceJpaRepository;
import com.conovax.fitflow.infrastructure.persistence.repositories.GymJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccessDeviceRepositoryAdapter implements AccessDeviceRepository {

	private final AccessDeviceJpaRepository jpaRepository;
	private final GymJpaRepository gymJpaRepository;
	private final AccessDeviceMapper mapper;

	@Override
	public AccessDevice save(AccessDevice device) {
		GymJpaEntity gym = gymJpaRepository.findById(device.getGymId())
				.orElseThrow(() -> new IllegalArgumentException("Gym no encontrado con ID: " + device.getGymId()));
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(device, gym)));
	}

	@Override
	public List<AccessDevice> findAllByGymId(Long gymId) {
		return jpaRepository.findAllByGym_Id(gymId).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public Optional<AccessDevice> findById(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public Optional<AccessDevice> findByIdAndGymId(Long id, Long gymId) {
		return jpaRepository.findByIdAndGym_Id(id, gymId).map(mapper::toDomain);
	}

	@Override
	public boolean existsById(Long id) {
		return jpaRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}
}
