package com.conovax.fitflow.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_devices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccessDeviceJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "access_device_id")
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gym_id", nullable = false)
	private GymJpaEntity gym;

	@Column(nullable = false, length = 100)
	private String name;

	/**
	 * Tipo de dispositivo: FINGERPRINT_READER, BARCODE_SCANNER, NFC_READER, PC, OTHER
	 */
	@Column(name = "device_type", nullable = false, length = 40)
	private String deviceType;

	@Column(name = "serial_number", length = 100)
	private String serialNumber;

	@Column(name = "mac_address", length = 17)
	private String macAddress;

	/** Ubicación física dentro del gym, ej: "Entrada principal" */
	@Column(length = 120)
	private String location;

	@Column(nullable = false)
	@Builder.Default
	private Boolean status = true;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void preUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
