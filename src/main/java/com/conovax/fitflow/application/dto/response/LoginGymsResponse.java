package com.conovax.sexbody.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Respuesta de login con gimnasios asociados")
public record LoginGymsResponse(
		@Schema(description = "Gyms asociados al usuario")
		List<GymInfoResponse> gyms
) {
}
