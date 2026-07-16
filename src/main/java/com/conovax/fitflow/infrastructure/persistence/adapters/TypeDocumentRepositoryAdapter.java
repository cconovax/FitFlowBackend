package com.conovax.sexbody.infrastructure.persistence.adapters;

import com.conovax.sexbody.domain.entities.TypeDocument;
import com.conovax.sexbody.domain.repositories.TypeDocumentRepository;
import com.conovax.sexbody.infrastructure.persistence.entities.TypeDocumentJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.mappers.TypeDocumentMapper;
import com.conovax.sexbody.infrastructure.persistence.repositories.TypeDocumentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TypeDocumentRepositoryAdapter implements TypeDocumentRepository {

	private final TypeDocumentJpaRepository jpaRepository;
	private final TypeDocumentMapper mapper;

	public TypeDocumentRepositoryAdapter(TypeDocumentJpaRepository jpaRepository, TypeDocumentMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public List<TypeDocument> findAllByStatusTrue() {
		return jpaRepository.findAllByStatusTrue().stream()
				.map(mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<TypeDocument> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<TypeDocument> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public TypeDocument save(TypeDocument typeDocument) {
		TypeDocumentJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(typeDocument));
		return mapper.toDomain(saved);
	}

	@Override
	public boolean existsById(Long id) {
		return jpaRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}
}
