package com.bsf.wallet.unit.service.impl;

import com.bsf.wallet.dto.request.TransferMoneyRequest;
import com.bsf.wallet.dto.response.TransferMoneyResponse;
import com.bsf.wallet.entity.Account;
import com.bsf.wallet.entity.Transaction;
import com.bsf.wallet.entity.TransactionFailLog;
import com.bsf.wallet.exception.ServiceException;
import com.bsf.wallet.repository.AccountRepository;
import com.bsf.wallet.repository.TransactionRepository;
import com.bsf.wallet.service.impl.TransactionFailLogService;
import com.bsf.wallet.service.impl.TransactionServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by: Tharindu Eranga
 * Date: 10 Jun 2022
 **/
@SpringBootTest
class TransactionServiceImplTest {

    private static final String REFERENCE = "ec74d79772363f752bd8c6a95ccc4bc2";
    private static final String DESCRIPTION = "test";

    @InjectMocks
    private TransactionServiceImpl transactionService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionFailLogService transactionFailLogService;


    @Test
    void successfulTransaction() {
        //new data unique to this test to avoid conflicts in next tests
        String reference = "8d449f69d429d2562e55faf816253ae5";
        BigDecimal amount = BigDecimal.valueOf(500.00);
        BigDecimal crBalanceBefore = BigDecimal.valueOf(2500.00);
        BigDecimal drBalanceBefore = BigDecimal.valueOf(4500.00);

        Optional<Account> crAccountOptional = Optional.of(new Account(1L, "TestUser1", "898955",
                crBalanceBefore, LocalDateTime.now()));
        Optional<Account> drAccountOptional = Optional.of(new Account(2L, "TestUser2", "663325",
                drBalanceBefore, LocalDateTime.now()));
        Account crAccount = crAccountOptional.get();
        Account drAccount = drAccountOptional.get();

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                crAccount.getId(),
                drAccount.getId(),
                amount,
                reference ,
                DESCRIPTION
        );

        when(accountRepository.findById(crAccount.getId())).thenReturn(crAccountOptional);
        when(accountRepository.findById(drAccount.getId())).thenReturn(drAccountOptional);
        when(transactionRepository.existsByReference(transferMoneyRequest.reference())).thenReturn(Boolean.FALSE);

        TransferMoneyResponse transferMoneyResponse = transactionService.transferMoney(transferMoneyRequest);

        assertEquals(transferMoneyResponse.transferRef(), transferMoneyRequest.reference());
        assertThat(crAccount.getBalance(), Matchers.comparesEqualTo(crBalanceBefore.add(amount)));
        assertThat(drAccount.getBalance(), Matchers.comparesEqualTo(drBalanceBefore.subtract(amount)));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void transferMoneyInvalidCrAccount() {
        Long validAccountId = 1L;
        Long invalidAccountId = 2L;
        Optional<Account> crAccountOptional = Optional.of(new Account(1L, "TestUser1", "898955",
                BigDecimal.TEN, LocalDateTime.now()));
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                validAccountId,
                invalidAccountId,
                BigDecimal.valueOf(50.00),
                REFERENCE,
                DESCRIPTION
        );

        when(accountRepository.findById(validAccountId)).thenReturn(crAccountOptional);
        when(accountRepository.findById(invalidAccountId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("No account found for id: %d".formatted(invalidAccountId), exception.getMessage());
        verify(transactionFailLogService, times(1))
                .saveFailTransaction(any(TransactionFailLog.class));
    }

    @Test
    void transferMoneyInvalidDrAccount() {
        Long validAccountId = 1L;
        Long invalidAccountId = 2L;
        Optional<Account> drAccountOptional = Optional.of(new Account(1L, "TestUser1", "898955",
                BigDecimal.TEN, LocalDateTime.now()));
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                invalidAccountId,
                validAccountId,
                BigDecimal.valueOf(50.00),
                REFERENCE,
                DESCRIPTION
        );

        when(accountRepository.findById(invalidAccountId)).thenReturn(Optional.empty());
        when(accountRepository.findById(validAccountId)).thenReturn(drAccountOptional);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("No account found for id: %d".formatted(invalidAccountId), exception.getMessage());
        verify(transactionFailLogService, times(1))
                .saveFailTransaction(any(TransactionFailLog.class));
    }

    @Test
    void transferMoneyDuplicateReference() {
        Optional<Account> crAccountOptional = Optional.of(new Account(1L, "TestUser1", "898955",
                BigDecimal.valueOf(500.00), LocalDateTime.now()));
        Optional<Account> drAccountOptional = Optional.of(new Account(2L, "TestUser2", "755955",
                BigDecimal.valueOf(500.00), LocalDateTime.now()));
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                crAccountOptional.get().getId(),
                drAccountOptional.get().getId(),
                BigDecimal.valueOf(50.00),
                REFERENCE,
                DESCRIPTION
        );

        when(accountRepository.findById(crAccountOptional.get().getId())).thenReturn(crAccountOptional);
        when(accountRepository.findById(drAccountOptional.get().getId())).thenReturn(drAccountOptional);
        when(transactionRepository.existsByReference(REFERENCE)).thenReturn(Boolean.TRUE);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("Reference already exists, please change and try again", exception.getMessage());
        verify(transactionFailLogService, times(1))
                .saveFailTransaction(any(TransactionFailLog.class));
    }

    @Test
    void transferMoneyToSameAccount() {
        Optional<Account> drAccountOptional = Optional.of(new Account(2L, "TestUser2", "755955",
                BigDecimal.valueOf(500.00), LocalDateTime.now()));
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                drAccountOptional.get().getId(),
                drAccountOptional.get().getId(),
                BigDecimal.valueOf(50.00),
                REFERENCE,
                DESCRIPTION
        );

        when(accountRepository.findById(drAccountOptional.get().getId())).thenReturn(drAccountOptional);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("Cannot transfer for the same account", exception.getMessage());
        verify(transactionFailLogService, times(1))
                .saveFailTransaction(any(TransactionFailLog.class));
    }

    @Test
    void transferMoneyWhenDrBalanceIsNotEnough() {
        Optional<Account> crAccountOptional = Optional.of(new Account(1L, "TestUser1", "898955",
                BigDecimal.valueOf(500.00), LocalDateTime.now()));
        Optional<Account> drAccountOptional = Optional.of(new Account(2L, "TestUser2", "755955",
                BigDecimal.valueOf(400.00), LocalDateTime.now()));
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                crAccountOptional.get().getId(),
                drAccountOptional.get().getId(),
                BigDecimal.valueOf(500.00),
                REFERENCE,
                DESCRIPTION
        );

        when(accountRepository.findById(crAccountOptional.get().getId())).thenReturn(crAccountOptional);
        when(accountRepository.findById(drAccountOptional.get().getId())).thenReturn(drAccountOptional);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> transactionService.transferMoney(transferMoneyRequest)
        );
        assertEquals("Not enough balance available for the transaction", exception.getMessage());
        verify(transactionFailLogService, times(1))
                .saveFailTransaction(any(TransactionFailLog.class));
    }

}