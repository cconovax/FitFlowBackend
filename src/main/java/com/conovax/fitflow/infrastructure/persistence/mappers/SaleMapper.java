package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.Sale;
import com.conovax.sexbody.infrastructure.persistence.entities.SaleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

	public Sale toDomain(SaleJpaEntity e) {
		if (e == null) return null;
		return Sale.builder()
				.id(e.getId())
				.gymId(e.getGymId())
				.sellerId(e.getSellerId())
				.saleDate(e.getSaleDate())
				.subtotal(e.getSubtotal())
				.total(e.getTotal())
				.paymentMethod(e.getPaymentMethod())
				.paidWith(e.getPaidWith())
				.changeAmount(e.getChangeAmount())
				.notes(e.getNotes())
				.status(e.getStatus())
				.build();
	}

	public SaleJpaEntity toJpaEntity(Sale d) {
		if (d == null) return null;
		return SaleJpaEntity.builder()
				.id(d.getId())
				.gymId(d.getGymId())
				.sellerId(d.getSellerId())
				.saleDate(d.getSaleDate())
				.subtotal(d.getSubtotal())
				.total(d.getTotal())
				.paymentMethod(d.getPaymentMethod())
				.paidWith(d.getPaidWith())
				.changeAmount(d.getChangeAmount())
				.notes(d.getNotes())
				.status(d.getStatus())
				.build();
	}
}
