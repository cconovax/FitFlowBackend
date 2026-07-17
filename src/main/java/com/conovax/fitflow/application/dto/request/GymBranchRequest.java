package com.conovax.fitflow.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para crear o actualizar una sede (sin logo, se envía como archivo multipart 'logo')")
public class GymBranchRequest {

	@NotBlank(message = "El nombre es requerido")
	@Size(max = 60, message = "El nombre no puede exceder 60 caracteres")
	@Schema(description = "Nombre de la sede", example = "SexBody Norte")
	private String name;

	@NotNull(message = "El ID del municipio es requerido")
	@Schema(description = "ID del municipio", example = "10")
	private Long municipalitieId;

	@Size(max = 13, message = "El teléfono no puede exceder 13 caracteres")
	@Schema(description = "Teléfono", example = "+573001234567")
	private String phone;

	@Size(max = 100, message = "El email no puede exceder 100 caracteres")
	@Schema(description = "Email", example = "norte@sexbody.com")
	private String email;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Long getMunicipalitieId() { return municipalitieId; }
	public void setMunicipalitieId(Long municipalitieId) { this.municipalitieId = municipalitieId; }

	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
}
