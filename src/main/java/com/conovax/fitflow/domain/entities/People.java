package com.conovax.sexbody.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class People {
	private Long id;
	private String names;
	private String surnames;
	private String phone;
	private String email;
	private String photo;
	private Long municipalitieId;
	private Long sexoId;
	private Long typeDocumentId;
	private String numDocument;
	private Boolean status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
