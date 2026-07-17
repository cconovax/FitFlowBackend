package com.conovax.fitflow.infrastructure.persistence.repositories;

import com.conovax.fitflow.infrastructure.persistence.entities.AccessDeviceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessDeviceJpaRepository extends JpaRepository<AccessDeviceJpaEntity, Long> {
	List<AccessDeviceJpaEntity> findAllByGym_Id(Long gymId);

	Optional<AccessDeviceJpaEntity> findByIdAndGym_Id(Long id, Long gymId);
}
