package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

	Role save(Role role);

	Optional<Role> findById(Long id);

	Optional<Role> findByIdAndStatusTrue(Long id);

	Optional<Role> findByName(String name);

	Optional<Role> findByNameAndStatusTrue(String name);

	List<Role> findAllByGymId(Long gymId);

	List<Role> findAllByGymIdAndStatusTrue(Long gymId);

	Page<Role> findAllByGymIdAndStatusTrue(Long gymId, Pageable pageable);

	List<Role> findAllByGymIdOrUniversal(Long gymId);

	List<Role> findAllByGymIdOrGymIdIsNullAndStatusTrue(Long gymId);

	Page<Role> findAllByGymIdOrGymIdIsNullAndStatusTrue(Long gymId, Pageable pageable);

	List<Role> findAllByStatusTrue();

	Page<Role> findAllByStatusTrue(Pageable pageable);

	List<Role> findAllById(Iterable<Long> ids);

	List<Role> findAll();

	Page<Role> findAll(Pageable pageable);

	Page<Role> findAllByGymId(Long gymId, Pageable pageable);

	long count();

	boolean existsByName(String name);

	boolean existsById(Long id);

	void deleteById(Long id);
}
