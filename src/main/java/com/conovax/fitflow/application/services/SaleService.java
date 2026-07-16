package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.SaleItemRequest;
import com.conovax.sexbody.application.dto.request.SaleRequest;
import com.conovax.sexbody.application.dto.response.SaleDetailResponse;
import com.conovax.sexbody.application.dto.response.SaleResponse;
import com.conovax.sexbody.application.pagination.PageResponse;
import com.conovax.sexbody.application.pagination.PaginationUtils;
import com.conovax.sexbody.domain.entities.Product;
import com.conovax.sexbody.domain.entities.Sale;
import com.conovax.sexbody.domain.entities.SaleDetail;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.ProductRepository;
import com.conovax.sexbody.domain.repositories.SaleDetailRepository;
import com.conovax.sexbody.domain.repositories.SaleRepository;
import com.conovax.sexbody.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

	private final SaleRepository saleRepository;
	private final SaleDetailRepository saleDetailRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	@Transactional
	public SaleResponse createSale(SaleRequest request) {
		List<SaleDetail> details = new ArrayList<>();
		BigDecimal subtotal = BigDecimal.ZERO;

		for (SaleItemRequest item : request.items()) {
			Product product = productRepository
					.findByIdAndGymIdAndStatusTrue(item.productId(), request.gymId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Producto no encontrado con ID: " + item.productId()));

			if (product.getCurrentStock() < item.amount()) {
				throw new IllegalArgumentException(
						"Stock insuficiente para \"" + product.getName()
								+ "\". Disponible: " + product.getCurrentStock()
								+ ", solicitado: " + item.amount());
			}

			BigDecimal lineSubtotal = product.getSalePraci()
					.multiply(BigDecimal.valueOf(item.amount()));
			subtotal = subtotal.add(lineSubtotal);

			details.add(SaleDetail.builder()
					.productId(product.getId())
					.amount(item.amount())
					.unitPrice(product.getSalePraci())
					.subtotal(lineSubtotal)
					.build());

			// Descontar stock
			Product updated = product.toBuilder()
					.currentStock(product.getCurrentStock() - item.amount())
					.updatedAt(java.time.LocalDate.now())
					.build();
			productRepository.save(updated);
		}

		Sale sale = Sale.builder()
				.gymId(request.gymId())
				.sellerId(request.sellerId())
				.saleDate(LocalDateTime.now())
				.subtotal(subtotal)
				.total(subtotal)
				.paymentMethod(request.paymentMethod())
				.paidWith(request.paidWith())
				.changeAmount(request.paidWith() != null ? request.paidWith().subtract(subtotal) : null)
				.notes(request.notes())
				.status(true)
				.build();

		Sale savedSale = saleRepository.save(sale);

		List<SaleDetail> savedDetails = new ArrayList<>();
		for (SaleDetail detail : details) {
			SaleDetail withSaleId = detail.toBuilder().saleId(savedSale.getId()).build();
			savedDetails.add(saleDetailRepository.save(withSaleId));
		}

		return toResponse(savedSale, savedDetails, null);
	}

	@Transactional(readOnly = true)
	public SaleResponse getById(Long gymId, Long saleId) {
		Sale sale = saleRepository.findById(saleId)
				.filter(s -> s.getGymId().equals(gymId))
				.orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + saleId));

		List<SaleDetail> details = saleDetailRepository.findAllBySaleId(saleId);

		List<Long> productIds = details.stream().map(SaleDetail::getProductId).distinct().toList();
		List<Product> products = productIds.stream()
				.map(pid -> productRepository.findByIdAndGymId(pid, gymId).orElse(null))
				.filter(p -> p != null)
				.toList();

		return toResponse(sale, details, products);
	}

	@Transactional(readOnly = true)
	public PageResponse<SaleResponse> getHistory(Long gymId, String from, String to, Integer page, Integer size) {
		Pageable pageable = PaginationUtils.pageable(page, size);

		LocalDateTime fromDt = (from != null && !from.isBlank())
				? LocalDateTime.parse(from + "T00:00:00")
				: LocalDateTime.of(1900, 1, 1, 0, 0, 0);
		LocalDateTime toDt = (to != null && !to.isBlank())
				? LocalDateTime.parse(to + "T23:59:59")
				: LocalDateTime.now().plusYears(100);

		Page<Sale> salesPage = saleRepository.findByGymIdAndDateRange(gymId, fromDt, toDt, pageable);

		return PaginationUtils.map(salesPage, sale -> {
			List<SaleDetail> details = saleDetailRepository.findAllBySaleId(sale.getId());
			List<Long> productIds = details.stream().map(SaleDetail::getProductId).distinct().toList();
			List<Product> products = productIds.stream()
					.map(pid -> productRepository.findByIdAndGymId(pid, gymId).orElse(null))
					.filter(p -> p != null)
					.toList();
			return toResponse(sale, details, products);
		});
	}

	private String resolveSellerName(Long sellerId) {
		if (sellerId == null) return null;
		return userRepository.findById(sellerId)
				.map(u -> {
					if (u.getPeople() == null) return null;
					String names = u.getPeople().getNames();
					String surnames = u.getPeople().getSurnames();
					if (names == null && surnames == null) return null;
					return ((names != null ? names : "") + " " + (surnames != null ? surnames : "")).trim();
				})
				.orElse(null);
	}

	private SaleResponse toResponse(Sale sale, List<SaleDetail> details, List<Product> products) {
		List<SaleDetailResponse> detailResponses = details.stream()
				.map(d -> {
					String productName = null;
					String productBarcode = null;
					if (products != null) {
						for (Product p : products) {
							if (p.getId().equals(d.getProductId())) {
								productName = p.getName();
								productBarcode = p.getBarcode();
								break;
							}
						}
					}
					return new SaleDetailResponse(
							d.getId(),
							d.getProductId(),
							productName,
							productBarcode,
							d.getAmount(),
							d.getUnitPrice(),
							d.getSubtotal()
					);
				})
				.toList();

		return new SaleResponse(
				sale.getId(),
				sale.getGymId(),
				sale.getSellerId(),
				resolveSellerName(sale.getSellerId()),
				sale.getSaleDate(),
				sale.getSubtotal(),
				sale.getTotal(),
				sale.getPaymentMethod(),
				sale.getPaidWith(),
				sale.getChangeAmount(),
				sale.getNotes(),
				sale.getStatus(),
				detailResponses
		);
	}
}
