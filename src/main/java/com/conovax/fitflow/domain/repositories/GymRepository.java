package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.Gym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GymRepository {
	Gym save(Gym gym);

	List<Gym> findAll();

	Page<Gym> findAll(Pageable pageable);

	List<Gym> findAllByStatusTrue();

	Optional<Gym> findById(Long id);

	Optional<Gym> findByIdAndStatusTrue(Long id);

	Optional<Gym> findByIdAndStatusFalse(Long id);

	List<Gym> findByNameContainingIgnoreCaseAndStatusTrue(String name);

	List<Gym> findByNameContainingIgnoreCase(String name);

	Page<Gym> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<Gym> findByNameContainingIgnoreCaseOrNitContainingIgnoreCase(String name, String nit, Pageable pageable);

	List<Gym> findByParentGymId(Long parentGymId);

	boolean existsById(Long id);

	void deleteById(Long id);
}
