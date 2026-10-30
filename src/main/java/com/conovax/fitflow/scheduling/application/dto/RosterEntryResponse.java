package com.conovax.fitflow.scheduling.application.dto;

public record RosterEntryResponse(
        Long reservationId, Long userGymId, String fullName, String status, String attendanceConfirmation) {}
