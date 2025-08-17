package com.example.wallet.Exception;

import java.util.UUID;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(UUID walletId) {
        super("Кошелек с ID " + walletId + " не найден");
    }
}