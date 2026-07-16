package com.conovax.sexbody.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para actualizar un gym (sin logo, se envía como archivo multipart 'logo' opcional)")
public class GymUpdateRequest {
	@NotBlank(message = "El nombre es requerido")
	@Size(max = 60, message = "El nombre no puede exceder 60 caracteres")
	@Schema(description = "Nombre", example = "SexBody Gym")
	private String name;

	@NotBlank(message = "El NIT es requerido")
	@Size(max = 20, message = "El NIT no puede exceder 20 caracteres")
	@Schema(description = "NIT", example = "900123456-7")
	private String nit;

	@NotNull(message = "El ID del municipio es requerido")
	@Schema(description = "ID del municipio", example = "10")
	private Long municipalitieId;

	@Size(max = 13, message = "El teléfono no puede exceder 13 caracteres")
	@Schema(description = "Teléfono", example = "+573001234567")
	private String phone;

	@Email(message = "El email no tiene un formato válido")
	@Size(max = 100, message = "El email no puede exceder 100 caracteres")
	@Schema(description = "Email", example = "info@sexbody.com")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public Long getMunicipalitieId() {
		return municipalitieId;
	}

	public void setMunicipalitieId(Long municipalitieId) {
		this.municipalitieId = municipalitieId;
	}

	public void setMunicipalityId(Long municipalityId) {
		this.municipalitieId = municipalityId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
