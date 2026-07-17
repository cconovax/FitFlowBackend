package com.conovax.fitflow.domain.repositories;

import com.conovax.fitflow.domain.entities.SessionLog;

import java.util.List;

public interface SessionLogRepository {
	List<SessionLog> findAllByUserGymIdOrderByStartDateDesc(Long userGymId);
}
