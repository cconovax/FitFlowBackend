package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.AuditLog;
import com.conovax.sexbody.domain.repositories.AuditLogRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.AuditLogJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.AuditLogMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.AuditLogJpaRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuditLogRepositoryAdapter implements AuditLogRepository {

	private final AuditLogJpaRepository jpaRepository;
	private final AuditLogMapper mapper;

	@Override
	public AuditLog save(AuditLog log) {
		return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(log)));
	}

	@Override
	public Page<AuditLog> search(
			Long gymId,
			Long userId,
			String action,
			LocalDateTime from,
			LocalDateTime to,
			String query,
			Pageable pageable
	) {
		// Criteria dinámica: los filtros nulos no agregan predicado (ni parámetro),
		// evitando los problemas de inferencia de tipos de PostgreSQL con parámetros nulos.
		Specification<AuditLogJpaEntity> spec = (root, criteria, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("gymId"), gymId));
			if (userId != null) {
				predicates.add(cb.equal(root.get("userId"), userId));
			}
			if (action != null && !action.isBlank()) {
				predicates.add(cb.equal(root.get("action"), action));
			}
			if (from != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), from));
			}
			if (to != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), to));
			}
			if (query != null && !query.isBlank()) {
				String like = "%" + query.toLowerCase() + "%";
				predicates.add(cb.or(
						cb.like(cb.lower(root.get("path")), like),
						cb.like(cb.lower(root.get("ipAddress")), like),
						cb.like(cb.lower(root.get("username")), like)
				));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return jpaRepository.findAll(spec, pageable).map(mapper::toDomain);
	}
}
