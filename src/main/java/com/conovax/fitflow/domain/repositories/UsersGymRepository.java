package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.UserGymUserPeople;
import com.conovax.fitflow.domain.entities.UsersGym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsersGymRepository {
	UsersGym save(UsersGym entity);

	void deleteById(Long id);

	Optional<UsersGym> findById(Long id);

	Optional<UsersGym> findByIdAndStatusTrue(Long id);

	Optional<UsersGym> findByIdAndStatusFalse(Long id);

	Optional<UsersGym> findByIdAndGymIdAndStatusTrue(Long id, Long gymId);

	Optional<UsersGym> findByIdAndGymIdAndStatusFalse(Long id, Long gymId);

	Optional<UsersGym> findByUserIdAndGymId(Long userId, Long gymId);

	List<UsersGym> findAllByUserId(Long userId);

	Optional<UsersGym> findByUserIdAndGymIdAndStatusTrue(Long userId, Long gymId);

	boolean existsByUserIdAndGymId(Long userId, Long gymId);

	boolean existsByUserIdAndGymIdAndStatusTrue(Long userId, Long gymId);

	Page<UserGymUserPeople> findUsersByGymId(Long gymId, String search, Pageable pageable);

	java.util.Optional<UserGymUserPeople> findUserGymPeopleById(Long userGymId);

	java.util.List<UserGymUserPeople> findTrainersByGymId(Long gymId, String search);
}
