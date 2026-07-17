package com.conovax.fitflow.infrastructure.persistence.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ProductInventoryProjection {
	Long getId();
	String getName();
	String getBarcode();
	BigDecimal getSalepraci();
	Integer getCurrentstock();
	Long getUnitssold();
	BigDecimal getRevenue();
	LocalDateTime getLastsaledate();
}
