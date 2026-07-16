package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.SaleDetail;
import com.conovax.sexbody.infrastructure.persistence.entities.SaleDetailJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SaleDetailMapper {

	public SaleDetail toDomain(SaleDetailJpaEntity e) {
		if (e == null) return null;
		return SaleDetail.builder()
				.id(e.getId())
				.saleId(e.getSaleId())
				.productId(e.getProductId())
				.amount(e.getAmount())
				.unitPrice(e.getUnitPrice())
				.subtotal(e.getSubtotal())
				.build();
	}

	public SaleDetailJpaEntity toJpaEntity(SaleDetail d) {
		if (d == null) return null;
		return SaleDetailJpaEntity.builder()
				.id(d.getId())
				.saleId(d.getSaleId())
				.productId(d.getProductId())
				.amount(d.getAmount())
				.unitPrice(d.getUnitPrice())
				.subtotal(d.getSubtotal())
				.build();
	}
}
