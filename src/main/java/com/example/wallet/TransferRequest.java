package com.example.wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull
        @Schema(description = "ID кошелька", example = "bce99cfe-2e42-4813-80dd-5c1cb41b7b4a")
        UUID id,

        @NotNull
        @DecimalMin(value = "0.01", message = "Сумма должна быть больше 0")
        @Schema(description = "Сумма операции", example = "1000.00")
        BigDecimal amount,

        @NotNull
        @Schema(description = "Тип операции", example = "DEPOSIT")
        OperationType operationType
) {
}