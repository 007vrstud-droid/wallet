package com.example.wallet;

import com.example.wallet.Exception.WalletNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final TransferService transferService;
    private final WalletRepository walletRepository;
    private final WalletProperties walletProperties;

    public void transferWithRetry(TransferRequest transferRequest) {
        int retries = walletProperties.getMaxOptimisticRetries();
        while (retries > 0) {
            try {
                transferService.attemptTransfer(transferRequest, Strategy.OPTIMISTIC);
                log.debug("Оптимистическая транзакция прошла успешно");
                return;
            } catch (OptimisticLockException e) {
                retries--;
                log.warn("Оптимистическая транзакция не прошла, еще попыток {}", retries);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Операция прервана", ie);
                }
            }
        }

        log.debug("Пессимистическая блокировка");
        transferService.attemptTransfer(transferRequest, Strategy.PESSIMISTIC);
        log.debug("Пессимистическая транзакция удалась");
    }

    public WalletInfoResponse getWalletInfo(UUID id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        return new WalletInfoResponse(wallet.getId(), wallet.getAmount());
    }
}