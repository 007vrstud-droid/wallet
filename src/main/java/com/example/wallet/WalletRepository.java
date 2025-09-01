package com.example.wallet;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w WHERE w.id = :id")
    Optional<Wallet> findByIdLock(@Param("id") UUID id);

    @Modifying
    @Query("update Wallet w set w.amount = w.amount + :amount where w.id = :id")
    int deposit(
            @Param("id") UUID id,
            @Param("amount") BigDecimal amount
    );

    @Modifying
    @Query("update Wallet w set w.amount = w.amount - :amount " +
            "where w.id = :id " +
            "and w.amount >= :amount")
    int withdraw(
            @Param("id") UUID id,
            @Param("amount") BigDecimal amount
    );
}