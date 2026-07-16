package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.Sexo;

import java.util.List;
import java.util.Optional;

public interface SexoRepository {
	List<Sexo> findAllByStatusTrue();

	Optional<Sexo> findByIdAndStatusTrue(Long id);

	Optional<Sexo> findByIdAndStatusFalse(Long id);

	Sexo save(Sexo sexo);

	boolean existsById(Long id);

	void deleteById(Long id);
}
