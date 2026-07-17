package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "peoples")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PeopleJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "people_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 60)
	private String names;

	@Column(nullable = false, length = 60)
	private String surnames;

	@Column(length = 13)
	private String phone;

	@Column(length = 60)
	private String email;

	@Column(nullable = false, length = 60)
	private String photo;

	@Column(name = "municipalitie_id", nullable = false)
	private Long municipalitieId;

	@Column(name = "sexo_id", nullable = false)
	private Long sexoId;

	@Column(name = "type_document_id", nullable = false)
	private Long typeDocumentId;

	@Column(name = "num_document", nullable = false, length = 15)
	private String numDocument;

	@Column(nullable = false)
	private Boolean status;

}
