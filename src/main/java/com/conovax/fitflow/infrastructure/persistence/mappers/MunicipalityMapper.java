package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.Municipality;
import com.conovax.fitflow.infrastructure.persistence.entities.MunicipalityJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityMapper {

	public Municipality toDomain(MunicipalityJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Municipality.builder()
				.id(jpaEntity.getId())
				.departamentId(jpaEntity.getDepartamentId())
				.name(jpaEntity.getName())
				.status(jpaEntity.getStatus())
				.build();
	}

	public MunicipalityJpaEntity toJpaEntity(Municipality domain) {
		if (domain == null) return null;

		return MunicipalityJpaEntity.builder()
				.id(domain.getId())
				.departamentId(domain.getDepartamentId())
				.name(domain.getName())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
