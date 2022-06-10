package com.bsf.wallet.unit.controller;

import com.bsf.wallet.dto.request.TransferMoneyRequest;
import com.bsf.wallet.dto.response.TransferMoneyResponse;
import com.bsf.wallet.service.impl.TransactionServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by: Tharindu Eranga
 * Date: 10 Jun 2022
 **/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private TransactionServiceImpl transactionService;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @SneakyThrows
    @Test
    void transferMoney() {
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                new Random().nextLong(),
                new Random().nextLong(),
                BigDecimal.TEN,
                "ec74d79772363f752bd8c6a95ccc4bc2",
                "test"
        );
        TransferMoneyResponse transferMoneyResponse = new TransferMoneyResponse(new Random().nextLong(),
                transferMoneyRequest.reference());
        when(transactionService.transferMoney(transferMoneyRequest)).thenReturn(transferMoneyResponse);

        mockMvc.perform(post("/v1/transaction/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transferMoneyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transferRef", Matchers.equalTo(transferMoneyResponse.transferRef())));
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
