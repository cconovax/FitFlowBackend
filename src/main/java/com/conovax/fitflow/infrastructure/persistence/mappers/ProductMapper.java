package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.Product;
import com.conovax.fitflow.infrastructure.persistence.entities.ProductJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

	public Product toDomain(ProductJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return Product.builder()
				.id(jpaEntity.getId())
				.gymId(jpaEntity.getGymId())
				.barcode(jpaEntity.getBarcode())
				.name(jpaEntity.getName())
				.description(jpaEntity.getDescription())
				.productsCategirieId(jpaEntity.getProductsCategirieId())
				.salePraci(jpaEntity.getSalePraci())
				.buyPraci(jpaEntity.getBuyPraci())
				.currentStock(jpaEntity.getCurrentStock())
				.minStock(jpaEntity.getMinStock())
				.currencieId(jpaEntity.getCurrencieId())
				.createdAt(jpaEntity.getCreatedAt())
				.updatedAt(jpaEntity.getUpdatedAt())
				.status(jpaEntity.getStatus())
				.build();
	}

	public ProductJpaEntity toJpaEntity(Product domain) {
		if (domain == null) return null;

		return ProductJpaEntity.builder()
				.id(domain.getId())
				.gymId(domain.getGymId())
				.barcode(domain.getBarcode())
				.name(domain.getName())
				.description(domain.getDescription())
				.productsCategirieId(domain.getProductsCategirieId())
				.salePraci(domain.getSalePraci())
				.buyPraci(domain.getBuyPraci())
				.currentStock(domain.getCurrentStock())
				.minStock(domain.getMinStock())
				.currencieId(domain.getCurrencieId())
				.createdAt(domain.getCreatedAt())
				.updatedAt(domain.getUpdatedAt())
				.status(domain.getStatus())
				.build();
	}
}
