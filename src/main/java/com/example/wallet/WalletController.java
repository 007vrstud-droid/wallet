package com.example.wallet;

import io.swagger.v3.oas.annotations.Parameter;
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
        walletService.transfer(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletInfoResponse> getWalletInfo(
            @Parameter(description = "UUID кошелька", example = "bce99cfe-2e42-4813-80dd-5c1cb41b7b4a")
            @PathVariable UUID id) {
        WalletInfoResponse walletInfoResponse = walletService.getWalletInfo(id);
        return ResponseEntity.ok(walletInfoResponse);
    }
}