package com.conovax.fitflow.shared.infrastructure.persistence.mappers;

import com.conovax.fitflow.shared.domain.entities.Sexo;
import com.conovax.fitflow.shared.infrastructure.persistence.entities.SexoJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SexoMapper {

	public Sexo toDomain(SexoJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Sexo.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getName())
				.code(jpaEntity.getCode())
				.status(jpaEntity.getStatus())
				.build();
	}

	public SexoJpaEntity toJpaEntity(Sexo domain) {
		if (domain == null) return null;

		return SexoJpaEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.code(domain.getCode())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
