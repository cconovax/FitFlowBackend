package com.conovax.fitflow.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessDevice {
	private Long id;
	private Long gymId;
	private String name;
	private String deviceType;
	private String serialNumber;
	private String macAddress;
	private String location;
	private Boolean status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
