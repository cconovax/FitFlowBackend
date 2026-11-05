package com.conovax.fitflow.scheduling.application.services;

import com.conovax.fitflow.scheduling.infrastructure.persistence.repositories.ClassScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassSessionMaterializationJob {
    private final ClassScheduleJpaRepository scheduleRepository;
    private final SchedulingService schedulingService;

    @Scheduled(cron = "0 0 * * * *")
    public void materializeRollingWindow() {
        List<Long> gymIds = scheduleRepository.findAllByStatus("ACTIVE").stream()
                .map(schedule -> schedule.getGymId())
                .distinct()
                .toList();
        log.info("Job de materialización de sesiones iniciado: gimnasios={}", gymIds.size());

        int totalCreated = 0;
        int failedGyms = 0;
        for (Long gymId : gymIds) {
            try {
                totalCreated += schedulingService.materializeWindow(gymId, LocalDate.now());
            } catch (Exception e) {
                failedGyms++;
                log.error("Falló la materialización de sesiones: gymId={}", gymId, e);
            }
        }
        log.info("Job de materialización de sesiones finalizado: gimnasios={}, fallidos={}, sesionesCreadas={}",
                gymIds.size(), failedGyms, totalCreated);
    }
}
