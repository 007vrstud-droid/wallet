package com.example.wallet;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletInfoDTO(
        UUID id,
        BigDecimal amount
) {}