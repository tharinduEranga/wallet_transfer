package com.bsf.wallet.integration.service.impl;

import com.bsf.wallet.dto.request.TransferMoneyRequest;
import com.bsf.wallet.dto.response.TransferMoneyResponse;
import com.bsf.wallet.entity.Account;
import com.bsf.wallet.entity.Transaction;
import com.bsf.wallet.exception.ServiceException;
import com.bsf.wallet.repository.AccountRepository;
import com.bsf.wallet.repository.TransactionRepository;
import com.bsf.wallet.service.impl.TransactionServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by: Tharindu Eranga
 * Date: 09 Jun 2022
 **/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
class TransactionServiceImplTest {

    private static final String REFERENCE = "ec74d79772363f752bd8c6a95ccc4bc2";
    private static final String DESCRIPTION = "test";

    @Autowired
    private TransactionServiceImpl transactionService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private Account crAccount;
    private Account drAccount;
    private final Long invalidAccountId = 10000L;

    @BeforeAll
    void setUp() {
        crAccount = new Account(1L, "John", "323232",
                BigDecimal.valueOf(100.00), LocalDateTime.now());
        drAccount = new Account(2L, "Anna", "898787",
                BigDecimal.valueOf(1200.00), LocalDateTime.now());
        accountRepository.saveAll(asList(crAccount, drAccount));

        Transaction transaction = Transaction.builder()
                .crAccount(crAccount)
                .drAccount(drAccount)
                .description(DESCRIPTION)
                .amount(BigDecimal.TEN)
                .crNewBalance(BigDecimal.TEN)
                .drNewBalance(BigDecimal.TEN)
                .reference(REFERENCE + DESCRIPTION)
                .build();
        transactionRepository.save(transaction);
    }

    @Test
    void successfulTransaction() {
        //new data unique to this test to avoid conflicts in next tests
        Account crAccount = new Account(null, "TestUser1", "898955",
                BigDecimal.valueOf(2500.00), LocalDateTime.now());
        Account drAccount = new Account(null, "TestUser2", "663325",
                BigDecimal.valueOf(4500.00), LocalDateTime.now());
        crAccount = accountRepository.save(crAccount);
        drAccount = accountRepository.save(drAccount);
        String reference = "8d449f69d429d2562e55faf816253ae5";
        BigDecimal amount = BigDecimal.valueOf(50.00);

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                crAccount.getId(),
                drAccount.getId(),
                amount,
                reference ,
                DESCRIPTION
        );
        TransferMoneyResponse transferMoneyResponse = transactionService.transferMoney(transferMoneyRequest);

        Optional<Account> crAccountAfterTransfer = accountRepository.findById(crAccount.getId());
        Optional<Account> drAccountAfterTransfer = accountRepository.findById(drAccount.getId());
        boolean transactionExists = transactionRepository.existsByReference(transferMoneyRequest.reference());

        assertTrue(crAccountAfterTransfer.isPresent());
        assertTrue(drAccountAfterTransfer.isPresent());
        assertEquals(transferMoneyResponse.transferRef(), transferMoneyRequest.reference());

        /*check whether all the related DB data are modified accordingly*/
        assertTrue(transactionExists);
        assertThat(crAccountAfterTransfer.get().getBalance(),
                Matchers.comparesEqualTo(crAccount.getBalance().add(amount)));
        assertThat(drAccountAfterTransfer.get().getBalance(),
                Matchers.comparesEqualTo(drAccount.getBalance().subtract(amount)));
    }

    @Test
    void transferMoneyInvalidCrAccount() {
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                invalidAccountId,
                drAccount.getId(),
                BigDecimal.valueOf(50.00),
                REFERENCE,
                DESCRIPTION
        );
        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("No account found for id: %d".formatted(invalidAccountId), exception.getMessage());
    }

    @Test
    void transferMoneyInvalidDrAccount() {
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                crAccount.getId(),
                invalidAccountId,
                BigDecimal.valueOf(50.00),
                REFERENCE,
                DESCRIPTION
        );
        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("No account found for id: %d".formatted(invalidAccountId), exception.getMessage());
    }

    @Test
    void transferMoneyDuplicateReference() {
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                crAccount.getId(),
                drAccount.getId(),
                BigDecimal.valueOf(50.00),
                REFERENCE + DESCRIPTION,
                DESCRIPTION
        );
        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("Reference already exists, please change and try again", exception.getMessage());
    }

    @Test
    void transferMoneyToSameAccount() {
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                crAccount.getId(),
                crAccount.getId(),
                BigDecimal.valueOf(50.00),
                REFERENCE,
                DESCRIPTION
        );
        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("Cannot transfer for the same account", exception.getMessage());
    }

    @Test
    void transferMoneyWhenDrBalanceIsNotEnough() {
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                crAccount.getId(),
                drAccount.getId(),
                BigDecimal.valueOf(100000.00),
                REFERENCE,
                DESCRIPTION
        );
        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("Not enough balance available for the transaction", exception.getMessage());
    }
}