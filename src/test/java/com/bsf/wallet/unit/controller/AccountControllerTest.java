package com.bsf.wallet.unit.controller;

import com.bsf.wallet.dto.response.AccountDetail;
import com.bsf.wallet.service.impl.AccountServiceImpl;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by: Tharindu Eranga
 * Date: 10 Jun 2022
 **/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @SneakyThrows
    @Test
    void getAccounts() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<AccountDetail> accounts = Arrays.asList(
                new AccountDetail(new Random().nextLong(), "John",
                        "323232", BigDecimal.TEN, LocalDateTime.now()),
                new AccountDetail(new Random().nextLong(), "Anna",
                        "898787", BigDecimal.TEN, LocalDateTime.now())
        );
        when(accountService.getAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/v1/account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(accounts.size())))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(accounts.get(0).id())))
                .andExpect(jsonPath("$[1].id", Matchers.equalTo(accounts.get(1).id())));
    }
}