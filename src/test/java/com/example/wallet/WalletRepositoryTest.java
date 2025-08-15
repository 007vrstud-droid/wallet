package com.example.wallet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    void testCreateAndFindWallet() {
        UUID walletId = UUID.randomUUID();

        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        wallet.setAmount(new BigDecimal("123.45"));
        wallet.setVersion(0L);

        walletRepository.save(wallet);

        Optional<Wallet> found = walletRepository.findById(walletId);

        assertThat(found).isPresent();
        assertThat(found.get().getAmount()).isEqualByComparingTo("123.45");
        assertThat(found.get().getVersion()).isEqualTo(0L);
    }
}