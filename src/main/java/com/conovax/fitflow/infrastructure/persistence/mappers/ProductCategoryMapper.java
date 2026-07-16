package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.ProductCategory;
import com.conovax.sexbody.infrastructure.persistence.entities.ProductCategoryJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {

	public ProductCategory toDomain(ProductCategoryJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return ProductCategory.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getName())
				.status(jpaEntity.getStatus())
				.build();
	}

	public ProductCategoryJpaEntity toJpaEntity(ProductCategory domain) {
		if (domain == null) return null;

		return ProductCategoryJpaEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.status(domain.getStatus())
				.build();
	}
}
