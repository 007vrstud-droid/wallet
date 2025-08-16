package com.example.wallet;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;


public record TransferRequest(
        @NotNull
        UUID id,
        @NotNull
        @DecimalMin(value = "0.01", message = "Сумма должна быть больше 0")
        BigDecimal amount,
        @NotNull
        OperationType operationType) {
}