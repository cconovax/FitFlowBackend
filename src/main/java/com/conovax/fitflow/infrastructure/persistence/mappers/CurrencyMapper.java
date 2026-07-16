package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.Currency;
import com.conovax.sexbody.infrastructure.persistence.entities.CurrencyJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {

	public Currency toDomain(CurrencyJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Currency.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getName())
				.code(jpaEntity.getCode())
				.status(jpaEntity.getStatus())
				.build();
	}

	public CurrencyJpaEntity toJpaEntity(Currency domain) {
		if (domain == null) return null;

		return CurrencyJpaEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.code(domain.getCode())
				.status(domain.getStatus())
				.build();
	}
}
