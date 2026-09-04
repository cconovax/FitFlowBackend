package com.conovax.fitflow.shared.infrastructure.persistence.repositories;

import com.conovax.fitflow.shared.infrastructure.persistence.entities.PasswordResetTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetTokenJpaRepository extends JpaRepository<PasswordResetTokenJpaEntity, Long> {
	Optional<PasswordResetTokenJpaEntity> findByToken(String token);

	@Modifying
	@Query("DELETE FROM PasswordResetTokenJpaEntity t WHERE t.userId = :userId")
	void deleteByUserId(@Param("userId") Long userId);
}
