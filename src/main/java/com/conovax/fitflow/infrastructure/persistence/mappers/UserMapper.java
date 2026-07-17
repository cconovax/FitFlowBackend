package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.People;
import com.conovax.fitflow.domain.entities.User;
import com.conovax.fitflow.infrastructure.persistence.entities.PeopleJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.entities.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public User toDomain(UserJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		People people = null;
		if (jpaEntity.getPeople() != null) {
			people = People.builder()
					.id(jpaEntity.getPeople().getId())
					.names(jpaEntity.getPeople().getNames())
					.surnames(jpaEntity.getPeople().getSurnames())
					.phone(jpaEntity.getPeople().getPhone())
					.email(jpaEntity.getPeople().getEmail())
					.photo(jpaEntity.getPeople().getPhoto())
					.municipalitieId(jpaEntity.getPeople().getMunicipalitieId())
					.sexoId(jpaEntity.getPeople().getSexoId())
					.typeDocumentId(jpaEntity.getPeople().getTypeDocumentId())
					.numDocument(jpaEntity.getPeople().getNumDocument())
					.status(jpaEntity.getPeople().getStatus())
					.createdAt(null)
					.updatedAt(null)
					.build();
		}

		return User.builder()
				.id(jpaEntity.getId())
				.people(people)
				.password(jpaEntity.getPassword())
				.build();
	}

	public UserJpaEntity toJpaEntity(User domain) {
		if (domain == null) return null;
		if (domain.getPeople() == null) {
			throw new IllegalArgumentException("User.people no puede ser null");
		}

		People peopleDomain = domain.getPeople();
		PeopleJpaEntity people = PeopleJpaEntity.builder()
				.id(peopleDomain.getId())
				.names(peopleDomain.getNames())
				.surnames(peopleDomain.getSurnames())
				.phone(peopleDomain.getPhone())
				.email(peopleDomain.getEmail())
				.photo(peopleDomain.getPhoto())
				.municipalitieId(peopleDomain.getMunicipalitieId())
				.sexoId(peopleDomain.getSexoId())
				.typeDocumentId(peopleDomain.getTypeDocumentId())
				.numDocument(peopleDomain.getNumDocument())
				.status(peopleDomain.getStatus() != null ? peopleDomain.getStatus() : Boolean.TRUE)
				.build();

		return UserJpaEntity.builder()
				.id(domain.getId())
				.people(people)
				.password(domain.getPassword())
				.build();
	}
}
