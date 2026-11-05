package com.conovax.fitflow.scheduling.application.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record PurchaseRequest(
        @NotNull Long sessionId,
        @NotNull Long userGymId,
        @NotNull @DecimalMin("0.00") BigDecimal amount,
        @NotBlank @Size(max = 50) String paymentMethod,
        @Size(max = 500) String notes) {}
