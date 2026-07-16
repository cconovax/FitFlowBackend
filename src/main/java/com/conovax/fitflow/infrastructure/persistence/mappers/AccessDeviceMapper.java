package com.conovax.sexbody.infrastructure.persistence.mappers;

import com.conovax.sexbody.domain.entities.AccessDevice;
import com.conovax.sexbody.infrastructure.persistence.entities.AccessDeviceJpaEntity;
import com.conovax.sexbody.infrastructure.persistence.entities.GymJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AccessDeviceMapper {

	public AccessDevice toDomain(AccessDeviceJpaEntity jpaEntity) {
		if (jpaEntity == null) {
			return null;
		}
		return AccessDevice.builder()
				.id(jpaEntity.getId())
				.gymId(jpaEntity.getGym() != null ? jpaEntity.getGym().getId() : null)
				.name(jpaEntity.getName())
				.deviceType(jpaEntity.getDeviceType())
				.serialNumber(jpaEntity.getSerialNumber())
				.macAddress(jpaEntity.getMacAddress())
				.location(jpaEntity.getLocation())
				.status(jpaEntity.getStatus())
				.createdAt(jpaEntity.getCreatedAt())
				.updatedAt(jpaEntity.getUpdatedAt())
				.build();
	}

	public AccessDeviceJpaEntity toJpaEntity(AccessDevice domain, GymJpaEntity gym) {
		if (domain == null) {
			return null;
		}
		return AccessDeviceJpaEntity.builder()
				.id(domain.getId())
				.gym(gym)
				.name(domain.getName())
				.deviceType(domain.getDeviceType())
				.serialNumber(domain.getSerialNumber())
				.macAddress(domain.getMacAddress())
				.location(domain.getLocation())
				.status(domain.getStatus() != null ? domain.getStatus() : Boolean.TRUE)
				.build();
	}
}
