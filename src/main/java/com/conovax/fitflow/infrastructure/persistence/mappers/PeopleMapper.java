package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.People;
import com.conovax.sexbody.infrastructure.persistence.entities.PeopleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class PeopleMapper {

	public People toDomain(PeopleJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return People.builder()
				.id(jpaEntity.getId())
				.names(jpaEntity.getNames())
				.surnames(jpaEntity.getSurnames())
				.phone(jpaEntity.getPhone())
				.email(jpaEntity.getEmail())
				.photo(jpaEntity.getPhoto())
				.municipalitieId(jpaEntity.getMunicipalitieId())
				.sexoId(jpaEntity.getSexoId())
				.typeDocumentId(jpaEntity.getTypeDocumentId())
				.numDocument(jpaEntity.getNumDocument())
				.status(jpaEntity.getStatus())
				.createdAt(null)
				.updatedAt(null)
				.build();
	}

	public PeopleJpaEntity toJpaEntity(People domain) {
		if (domain == null) return null;

		return PeopleJpaEntity.builder()
				.id(domain.getId())
				.names(domain.getNames())
				.surnames(domain.getSurnames())
				.phone(domain.getPhone())
				.email(domain.getEmail())
				.photo(domain.getPhoto())
				.municipalitieId(domain.getMunicipalitieId())
				.sexoId(domain.getSexoId())
				.typeDocumentId(domain.getTypeDocumentId())
				.numDocument(domain.getNumDocument())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
