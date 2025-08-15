package com.example.wallet;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest request) {
        walletService.transferWithRetry(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletInfoResponse> getWalletInfo(@PathVariable UUID id) {
        WalletInfoResponse walletInfoResponse = walletService.getWalletInfo(id);
        return ResponseEntity.ok(walletInfoResponse);
    }
}