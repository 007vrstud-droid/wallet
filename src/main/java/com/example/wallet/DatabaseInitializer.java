package com.example.wallet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements ApplicationRunner {
    private final WalletRepository walletRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (walletRepository.count() == 0) {
            Wallet wallet = new Wallet();
            wallet.setId(UUID.randomUUID());
            wallet.setAmount(BigDecimal.valueOf(1000));
            wallet.setVersion(0L);
            walletRepository.save(wallet);
            log.info("Создали кошелек id {}", wallet.getId());
        } else {
            UUID o = walletRepository.findAll().stream()
                    .findFirst()
                    .map(w -> w.getId())
                    .orElseThrow(() -> new RuntimeException("Если этот код запустится то писец"));
            log.info("Кошелек уже существует, id {}", o);
        }
    }
}