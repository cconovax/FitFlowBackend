package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.SaleDetail;

import java.util.List;

public interface SaleDetailRepository {

	SaleDetail save(SaleDetail detail);

	List<SaleDetail> findAllBySaleId(Long saleId);
}
