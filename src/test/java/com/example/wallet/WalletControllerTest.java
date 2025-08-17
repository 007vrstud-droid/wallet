package com.example.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    void getWallet_shouldReturnWalletById() throws Exception {

        UUID walletId = UUID.randomUUID();
        WalletInfoResponse mockResponse = new WalletInfoResponse(walletId, BigDecimal.valueOf(1000));

        when(walletService.getWalletInfo(walletId)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": "%s",
                            "amount": 1000
                        }
                        """.formatted(walletId.toString())));
    }

    @Test
    void transfer_shouldReturn400_whenAmountIsNegative() throws Exception {
        UUID walletId = UUID.randomUUID();
        String requestJson = new ObjectMapper().writeValueAsString(new TransferRequest(
                walletId,
                new BigDecimal("-100"),
                OperationType.DEPOSIT
        ));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("amount")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Сумма должна быть больше 0")));
    }
}
