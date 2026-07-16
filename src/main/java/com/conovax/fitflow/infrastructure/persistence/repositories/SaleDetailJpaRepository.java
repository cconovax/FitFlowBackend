package com.conovax.sexbody.infrastructure.persistence.repositories;

import com.conovax.sexbody.infrastructure.persistence.entities.SaleDetailJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleDetailJpaRepository extends JpaRepository<SaleDetailJpaEntity, Long> {

	List<SaleDetailJpaEntity> findAllBySaleId(Long saleId);

	void deleteAllBySaleId(Long saleId);
}
