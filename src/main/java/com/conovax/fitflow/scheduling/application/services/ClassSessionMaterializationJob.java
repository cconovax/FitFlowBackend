package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ClassSessionMaterializationJob {
    private final ClassScheduleJpaRepository scheduleRepository;
    private final SchedulingService schedulingService;

    @Scheduled(cron = "0 0 * * * *")
    public void materializeRollingWindow() {
        scheduleRepository.findAllByStatus("ACTIVE").stream()
                .map(schedule -> schedule.getGymId())
                .distinct()
                .forEach(gymId -> schedulingService.materializeWindow(gymId, LocalDate.now()));
    }
}
