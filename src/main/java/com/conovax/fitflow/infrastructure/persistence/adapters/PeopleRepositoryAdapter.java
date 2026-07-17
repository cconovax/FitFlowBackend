package com.conovax.fitflow.infrastructure.persistence.adapters;

import com.conovax.fitflow.domain.entities.People;
import com.conovax.fitflow.domain.repositories.PeopleRepository;
import com.conovax.fitflow.infrastructure.persistence.entities.PeopleJpaEntity;
import com.conovax.fitflow.infrastructure.persistence.mappers.PeopleMapper;
import com.conovax.fitflow.infrastructure.persistence.repositories.PeopleJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PeopleRepositoryAdapter implements PeopleRepository {

	private final PeopleJpaRepository jpaRepository;
	private final PeopleMapper mapper;

	public PeopleRepositoryAdapter(PeopleJpaRepository jpaRepository, PeopleMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Page<People> findAllByStatusTrue(Pageable pageable) {
		return jpaRepository.findAllByStatusTrue(pageable)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<People> findByIdAndStatusTrue(Long id) {
		return jpaRepository.findByIdAndStatusTrue(id)
				.map(mapper::toDomain);
	}

	@Override
	public Optional<People> findByIdAndStatusFalse(Long id) {
		return jpaRepository.findByIdAndStatusFalse(id)
				.map(mapper::toDomain);
	}

	@Override
	public boolean existsByNumDocumentAndStatusTrue(String numDocument) {
		return jpaRepository.existsByNumDocumentAndStatusTrue(numDocument);
	}

	@Override
	public boolean existsByEmailAndStatusTrue(String email) {
		return jpaRepository.existsByEmailAndStatusTrue(email);
	}

	@Override
	public boolean existsByNumDocumentAndStatusTrueAndIdNot(String numDocument, Long id) {
		return jpaRepository.existsByNumDocumentAndStatusTrueAndIdNot(numDocument, id);
	}

	@Override
	public boolean existsByEmailAndStatusTrueAndIdNot(String email, Long id) {
		return jpaRepository.existsByEmailAndStatusTrueAndIdNot(email, id);
	}

	@Override
	public People save(People people) {
		PeopleJpaEntity saved = jpaRepository.save(mapper.toJpaEntity(people));
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
