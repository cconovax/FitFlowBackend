package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.Departament;
import com.conovax.sexbody.infrastructure.persistence.entities.DepartamentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class DepartamentMapper {

	public Departament toDomain(DepartamentJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Departament.builder()
				.id(jpaEntity.getId())
				.contryId(jpaEntity.getContryId())
				.name(jpaEntity.getName())
				.status(jpaEntity.getStatus())
				.build();
	}

	public DepartamentJpaEntity toJpaEntity(Departament domain) {
		if (domain == null) return null;

		return DepartamentJpaEntity.builder()
				.id(domain.getId())
				.contryId(domain.getContryId())
				.name(domain.getName())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
