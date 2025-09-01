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

//    @Transactional
//    public void transferWithLocking(TransferRequest transferRequest, Strategy strategy) {
//        UUID id = transferRequest.id();
//        BigDecimal amount = transferRequest.amount();
//        OperationType operationType = transferRequest.operationType();
//
//        Wallet wallet = switch (strategy) {
//            case PESSIMISTIC -> walletRepository.findByIdLock(id)
//                    .orElseThrow(() -> new RuntimeException("Кошелек не найден"));
//            case OPTIMISTIC -> walletRepository.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Кошелек не найден"));
//        };
//        switch (operationType) {
//            case DEPOSIT -> wallet.setAmount(wallet.getAmount().add(amount));
//            case WITHDRAW -> {
//                if (wallet.getAmount().compareTo(amount) >= 0) {
//                    wallet.setAmount(wallet.getAmount().subtract(amount));
//                } else {
//                    throw new RuntimeException("Недостаточно средств");
//                }
//            }
//        }
//    }

    @Transactional
    public void transfer(TransferRequest transferRequest) {
        switch (transferRequest.operationType()) {
            case DEPOSIT -> {
                int rowsAffected =  walletRepository.deposit(transferRequest.id(),transferRequest.amount());
                if (rowsAffected == 0) {
                    throw new RuntimeException("Кошелек не найден");
                }
            }
            case WITHDRAW -> {
                int rowsAffected = walletRepository.withdraw(transferRequest.id(),transferRequest.amount());
                if (rowsAffected == 0) {
                    throw new RuntimeException("Кошелек не найден, либо недостаточно средств");
                }
            }
        }
    }
}
