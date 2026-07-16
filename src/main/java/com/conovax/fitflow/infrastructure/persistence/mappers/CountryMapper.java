package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.Country;
import com.conovax.sexbody.infrastructure.persistence.entities.CountryJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

	public Country toDomain(CountryJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Country.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getName())
				.code(jpaEntity.getCode())
				.status(jpaEntity.getStatus())
				.build();
	}

	public CountryJpaEntity toJpaEntity(Country domain) {
		if (domain == null) return null;

		return CountryJpaEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.code(domain.getCode())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
