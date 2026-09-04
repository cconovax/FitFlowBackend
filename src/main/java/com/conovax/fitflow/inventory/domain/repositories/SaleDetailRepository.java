package com.conovax.fitflow.inventory.domain.repositories;

import com.conovax.fitflow.inventory.domain.entities.SaleDetail;

import java.util.List;

public interface SaleDetailRepository {

	SaleDetail save(SaleDetail detail);

	List<SaleDetail> findAllBySaleId(Long saleId);
}
