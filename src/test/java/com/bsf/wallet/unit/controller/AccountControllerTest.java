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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

        List<AccountDetail> accounts = Arrays.asList(
                new AccountDetail(new Random().nextLong(), "John",
                        "323232", BigDecimal.TEN, LocalDateTime.now()),
                new AccountDetail(new Random().nextLong(), "Anna",
                        "898787", BigDecimal.TEN, LocalDateTime.now())
        );
        PageImpl<AccountDetail> accountsPage = new PageImpl<>(accounts);
        when(accountService.getAccounts(PageRequest.of(0, accounts.size()))).thenReturn(accountsPage);

        mockMvc.perform(get("/v1/account?size=2&page=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(accountsPage.getSize())))
                .andExpect(jsonPath("$.content[0].id", Matchers.equalTo(accounts.get(0).id())))
                .andExpect(jsonPath("$.content[1].id", Matchers.equalTo(accounts.get(1).id())));
    }
}
