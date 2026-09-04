package com.conovax.fitflow.shared.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.conovax.fitflow.gym.application.dto.response.GymInfoResponse;

import java.util.List;

@Schema(description = "Respuesta de login con gimnasios asociados")
public record LoginGymsResponse(
		@Schema(description = "Gyms asociados al usuario")
		List<GymInfoResponse> gyms
) {
}
