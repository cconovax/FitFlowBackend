package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.SessionLog;

import java.util.List;

public interface SessionLogRepository {
	List<SessionLog> findAllByUserGymIdOrderByStartDateDesc(Long userGymId);
}
