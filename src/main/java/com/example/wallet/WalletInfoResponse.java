package com.example.wallet;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletInfoResponse(
        UUID id,
        BigDecimal amount
) {}