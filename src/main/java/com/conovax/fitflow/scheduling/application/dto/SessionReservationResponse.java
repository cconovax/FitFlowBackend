package com.conovax.fitflow.scheduling.application.dto;

public record SessionReservationResponse(
        Long reservationId, Long userGymId, String fullName, String status, String attendanceConfirmation,
        Integer waitlistPosition) {}
