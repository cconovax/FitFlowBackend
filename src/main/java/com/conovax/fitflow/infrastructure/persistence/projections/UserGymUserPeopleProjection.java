package com.conovax.sexbody.infrastructure.persistence.projections;

public interface UserGymUserPeopleProjection {
	Long getUserGymId();
	Long getUserId();

	Boolean getUserGymStatus();

	Long getPeopleId();
	String getNumDocument();
	String getEmail();
	String getNames();
	String getSurnames();
	String getPhone();
}
