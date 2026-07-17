package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.Product;
import com.conovax.fitflow.domain.entities.ProductInventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
	Product save(Product product);

	List<Product> findAllByStatusTrue();

	List<Product> findAllByGymIdAndStatusTrue(Long gymId);

	Page<Product> findAllByGymIdAndStatusTrue(Long gymId, Pageable pageable);

	Page<Product> findPageByGymIdAndSearch(Long gymId, String search, Pageable pageable);

	Page<Product> findPageByGymIdAndSearchAndStatusTrue(Long gymId, String search, Pageable pageable);

	Optional<Product> findByIdAndStatusTrue(Long id);

	Optional<Product> findByIdAndGymIdAndStatusTrue(Long id, Long gymId);

	Optional<Product> findByIdAndStatusFalse(Long id);

	Optional<Product> findByIdAndGymId(Long id, Long gymId);

	Optional<Product> findByIdAndGymIdAndStatusFalse(Long id, Long gymId);

	List<Product> findByNameContainingIgnoreCaseAndStatusTrue(String name);

	List<Product> findByGymIdAndNameContainingIgnoreCaseAndStatusTrue(Long gymId, String name);

	boolean existsByBarcodeIgnoreCaseAndGymIdAndStatusTrue(String barcode, Long gymId);

	boolean existsByBarcodeIgnoreCaseAndGymIdAndIdNotAndStatusTrue(String barcode, Long gymId, Long id);

	java.util.Optional<Product> findByBarcodeIgnoreCaseAndGymIdAndStatusTrue(String barcode, Long gymId);

	void deleteById(Long id);

	List<ProductInventoryItem> findInventory(Long gymId, LocalDateTime from, LocalDateTime to);
}
