package com.example.wallet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferService {

    private final WalletRepository walletRepository;

    @Transactional
    public void attemptTransfer(TransferRequest transferRequest, Strategy strategy) {
        UUID id = transferRequest.getId();
        BigDecimal amount = transferRequest.getAmount();
        OperationType operationType = transferRequest.getOperationType();

        Wallet wallet = switch (strategy) {
            case PESSIMISTIC -> walletRepository.findByIdLock(id)
                    .orElseThrow(() -> new RuntimeException("Кошелек не найден"));
            case OPTIMISTIC -> walletRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Кошелек не найден"));
        };
        switch (operationType) {
            case DEPOSIT -> wallet.setAmount(wallet.getAmount().add(amount));
            case WITHDRAW -> {
                if (wallet.getAmount().compareTo(amount) >= 0) {
                    wallet.setAmount(wallet.getAmount().subtract(amount));
                } else {
                    throw new RuntimeException("Недостаточно средств");
                }
            }
        }
    }
}
