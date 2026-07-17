package com.conovax.fitflow.infrastructure.persistence.mappers;

import com.conovax.fitflow.domain.entities.TypeDocument;
import com.conovax.fitflow.infrastructure.persistence.entities.TypeDocumentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TypeDocumentMapper {

	public TypeDocument toDomain(TypeDocumentJpaEntity jpaEntity) {
		if (jpaEntity == null) return null;

		return TypeDocument.builder()
				.id(jpaEntity.getId())
				.name(jpaEntity.getName())
				.code(jpaEntity.getCode())
				.status(jpaEntity.getStatus())
				.build();
	}

	public TypeDocumentJpaEntity toJpaEntity(TypeDocument domain) {
		if (domain == null) return null;

		return TypeDocumentJpaEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.code(domain.getCode())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
