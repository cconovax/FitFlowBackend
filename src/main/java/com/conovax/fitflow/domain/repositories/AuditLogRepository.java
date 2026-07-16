package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AuditLogRepository {
	AuditLog save(AuditLog log);

	Page<AuditLog> search(
			Long gymId,
			Long userId,
			String action,
			LocalDateTime from,
			LocalDateTime to,
			String query,
			Pageable pageable
	);
}
