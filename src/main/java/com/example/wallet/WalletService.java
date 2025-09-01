package com.example.wallet;

import com.example.wallet.Exception.WalletNotFoundException;
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
//    private final WalletProperties walletProperties;

    public void transfer(TransferRequest transferRequest) {
//        int retries = walletProperties.getMaxOptimisticRetries();
//        while (retries > 0) {
//            try {
//                transferService.transferWithLocking(transferRequest, Strategy.OPTIMISTIC);
//                if (retries < walletProperties.getMaxOptimisticRetries()) {
//                    log.info("Прошло при повторной попытке");
//                }
//                return;
//            } catch (ObjectOptimisticLockingFailureException | OptimisticLockException e) {
//                retries--;
//                log.warn("Оптимистическая транзакция не прошла, еще попыток {}", retries);
////                try {
////                    Thread.sleep(5);
////                } catch (InterruptedException ie) {
////                    Thread.currentThread().interrupt();
////                    throw new RuntimeException("Операция прервана", ie);
////                }
//            }
//        }
//        log.info("Попытки исчерпаны");

//        log.debug("Пессимистическая блокировка");
//        transferService.transferWithLocking(transferRequest, Strategy.PESSIMISTIC);
//        log.debug("Пессимистическая транзакция удалась");

        transferService.transfer(transferRequest);
    }

    public WalletInfoResponse getWalletInfo(UUID id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        return new WalletInfoResponse(wallet.getId(), wallet.getAmount());
    }
}