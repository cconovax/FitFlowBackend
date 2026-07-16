package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SaleRepository {

	Sale save(Sale sale);

	Optional<Sale> findById(Long id);

	Page<Sale> findAllByGymIdAndStatusTrue(Long gymId, Pageable pageable);

	Page<Sale> findByGymIdAndDateRange(Long gymId, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
