package com.conovax.sexbody.application.services;

import com.conovax.sexbody.application.dto.request.SexoRequest;
import com.conovax.sexbody.application.dto.response.SexoResponse;
import com.conovax.sexbody.domain.entities.Sexo;
import com.conovax.sexbody.domain.exceptions.ResourceNotFoundException;
import com.conovax.sexbody.domain.repositories.SexoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SexoService {

	private final SexoRepository sexoRepository;

	@Transactional(readOnly = true)
	public List<SexoResponse> getAll() {
		return sexoRepository.findAllByStatusTrue().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public SexoResponse getById(Long id) {
		Sexo entity = sexoRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado con ID: " + id));
		return toResponse(entity);
	}

	@Transactional
	public SexoResponse create(SexoRequest request) {
		Sexo entity = Sexo.builder()
				.name(request.name())
				.code(request.code())
				.status(true)
				.build();
		return toResponse(sexoRepository.save(entity));
	}

	@Transactional
	public SexoResponse update(Long id, SexoRequest request) {
		Sexo entity = sexoRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado con ID: " + id));
		Sexo updated = entity.toBuilder()
				.name(request.name())
				.code(request.code())
				.build();
		return toResponse(sexoRepository.save(updated));
	}

	@Transactional
	public void deleteLogical(Long id) {
		Sexo entity = sexoRepository.findByIdAndStatusTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado con ID: " + id));
		sexoRepository.save(entity.toBuilder().status(false).build());
	}

	@Transactional
	public void reset(Long id) {
		Sexo entity = sexoRepository.findByIdAndStatusFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado con ID: " + id));
		sexoRepository.save(entity.toBuilder().status(true).build());
	}

	@Transactional
	public void forceDelete(Long id) {
		if (!sexoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Sexo no encontrado con ID: " + id);
		}
		sexoRepository.deleteById(id);
	}

	private SexoResponse toResponse(Sexo entity) {
		return new SexoResponse(entity.getId(), entity.getName(), entity.getCode(), entity.getStatus());
	}
}
