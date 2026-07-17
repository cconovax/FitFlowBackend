package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.SaleDetail;

import java.util.List;

public interface SaleDetailRepository {

	SaleDetail save(SaleDetail detail);

	List<SaleDetail> findAllBySaleId(Long saleId);
}
