package com.conovax.fitflow.application.services;

import com.conovax.fitflow.application.dto.response.AuditLogResponse;
import com.conovax.fitflow.application.pagination.PageResponse;
import com.conovax.fitflow.application.pagination.PaginationUtils;
import com.conovax.fitflow.domain.entities.AuditLog;
import com.conovax.fitflow.domain.repositories.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

	private final AuditLogRepository repository;

	/**
	 * Persiste un registro de auditoría. Pensado para ser invocado desde el filtro de
	 * captura; el caller debe envolver la llamada en try/catch para que un fallo de
	 * auditoría nunca rompa la petición original.
	 */
	@Transactional
	public void record(AuditLog log) {
		repository.save(log);
	}

	@Transactional(readOnly = true)
	public PageResponse<AuditLogResponse> search(
			Long gymId,
			Long userId,
			String action,
			String from,
			String to,
			String query,
			Integer page,
			Integer size
	) {
		LocalDateTime fromDate = parseStart(from);
		LocalDateTime toDate = parseEnd(to);
		String normalizedAction = (action == null || action.isBlank()) ? null : action.trim().toUpperCase();
		String normalizedQuery = (query == null || query.isBlank()) ? null : query.trim();

		Pageable pageable = PaginationUtils.pageable(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		return PaginationUtils.map(
				repository.search(gymId, userId, normalizedAction, fromDate, toDate, normalizedQuery, pageable),
				this::toResponse
		);
	}

	private LocalDateTime parseStart(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		return LocalDate.parse(value.trim()).atStartOfDay();
	}

	private LocalDateTime parseEnd(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		return LocalDate.parse(value.trim()).atTime(LocalTime.MAX);
	}

	private AuditLogResponse toResponse(AuditLog log) {
		return new AuditLogResponse(
				log.getId(),
				log.getGymId(),
				log.getUserId(),
				log.getUserGymId(),
				log.getUsername(),
				log.getAction(),
				log.getHttpMethod(),
				log.getPath(),
				log.getQueryString(),
				log.getPayload(),
				log.getIpAddress(),
				log.getUserAgent(),
				log.getStatusCode(),
				log.getCreatedAt()
		);
	}
}
