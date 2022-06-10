package com.bsf.wallet.integration.service.impl;

import com.bsf.wallet.dto.request.TransferMoneyRequest;
import com.bsf.wallet.entity.Account;
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
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by: Tharindu Eranga
 * Date: 10 Jun 2022
 **/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class TransactionServiceImplConcurrentTest {

    private static final String REFERENCE = "ec74d79772363f752bd8c6a95ccc4bc2";
    private static final String DESCRIPTION = "test";

    @Autowired
    private TransactionServiceImpl transactionService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeAll
    void setUp() {

    }

    @Test
    void testSummationWithConcurrency() throws InterruptedException {
        final Account crAccount = accountRepository.save(new Account(null, "TestUser1", "898955",
                BigDecimal.valueOf(2500.00), LocalDateTime.now()));
        ;
        final Account drAccount = accountRepository.save(new Account(null, "TestUser2", "663325",
                BigDecimal.valueOf(4500.00), LocalDateTime.now()));

        BigDecimal amount = BigDecimal.valueOf(10.00);

        int numberOfThreads = 100;
        BigDecimal totalAmount = amount.multiply(BigDecimal.valueOf(numberOfThreads));
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                try {
                    String reference = UUID.randomUUID().toString();
                    TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(
                            crAccount.getId(),
                            drAccount.getId(),
                            amount,
                            reference,
                            DESCRIPTION
                    );
                    executeTransaction(transferMoneyRequest);
                    boolean transactionExists = transactionRepository
                            .existsByReference(transferMoneyRequest.reference());
                    assertTrue(transactionExists);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }
        latch.await();

        Optional<Account> crAccountAfterTransfer = accountRepository.findById(crAccount.getId());
        Optional<Account> drAccountAfterTransfer = accountRepository.findById(drAccount.getId());

        assertTrue(crAccountAfterTransfer.isPresent());
        assertTrue(drAccountAfterTransfer.isPresent());

        /*check whether all the related DB data are modified accordingly*/

        assertThat(crAccountAfterTransfer.get().getBalance(),
                Matchers.comparesEqualTo(crAccount.getBalance().add(totalAmount)));
        assertThat(drAccountAfterTransfer.get().getBalance(),
                Matchers.comparesEqualTo(drAccount.getBalance().subtract(totalAmount)));
    }

    private void executeTransaction(TransferMoneyRequest transferMoneyRequest) {
        transactionService.transferMoney(transferMoneyRequest);
    }
}
