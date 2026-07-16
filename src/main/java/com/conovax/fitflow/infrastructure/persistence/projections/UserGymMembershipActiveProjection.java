package com.conovax.sexbody.infrastructure.persistence.projections;

import java.time.LocalDate;

public interface UserGymMembershipActiveProjection {
	Long getUserGymMembershipId();
	Long getUserGymId();
	Long getMembershipId();
	LocalDate getStartDate();
	LocalDate getEndDate();
	Boolean getStatus();
	Long getUserId();
	Long getPeopleId();
	String getNames();
	String getSurnames();
	String getEmail();
	String getPhone();
}
