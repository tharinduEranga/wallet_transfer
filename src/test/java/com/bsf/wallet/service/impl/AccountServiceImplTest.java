package com.bsf.wallet.service.impl;

import com.bsf.wallet.dto.response.AccountDetail;
import com.bsf.wallet.entity.Account;
import com.bsf.wallet.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by: Tharindu Eranga
 * Date: 08 Jun 2022
 **/
@SpringBootTest
@ActiveProfiles("test")
class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAccounts() {
        List<Account> accountsSaved = Arrays.asList(
                new Account(1L, "John", "323232", BigDecimal.TEN, LocalDateTime.now()),
                new Account(2L, "Anna", "898787", BigDecimal.TEN, LocalDateTime.now())
        );
        accountRepository.saveAll(accountsSaved);
        List<AccountDetail> fetchedAccounts = accountService.getAccounts();
        assertEquals(accountsSaved.size(), fetchedAccounts.size());
    }
}