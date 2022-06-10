package com.bsf.wallet.unit.service.impl;

import com.bsf.wallet.dto.response.AccountDetail;
import com.bsf.wallet.entity.Account;
import com.bsf.wallet.repository.AccountRepository;
import com.bsf.wallet.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by: Tharindu Eranga
 * Date: 10 Jun 2022
 **/
@SpringBootTest
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAccounts() {
        List<Account> accounts = Arrays.asList(
                new Account(1L, "John", "323232", BigDecimal.TEN, LocalDateTime.now()),
                new Account(2L, "Anna", "898787", BigDecimal.TEN, LocalDateTime.now())
        );
        when(accountRepository.findAll()).thenReturn(accounts);
        List<AccountDetail> fetchedAccounts = accountService.getAccounts();

        assertEquals(accounts.size(), fetchedAccounts.size());
        boolean contains = true;
        for (Account account : accounts) {
            contains = fetchedAccounts.stream()
                    .anyMatch(accountDetail -> accountDetail.id().equals(account.getId()));
            if (!contains) break;
        }
        assertTrue(contains);
    }
}