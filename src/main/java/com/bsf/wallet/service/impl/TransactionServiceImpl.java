package com.bsf.wallet.service.impl;

import com.bsf.wallet.dto.request.TransferMoneyRequest;
import com.bsf.wallet.entity.Account;
import com.bsf.wallet.entity.Transaction;
import com.bsf.wallet.entity.TransactionFailLog;
import com.bsf.wallet.exception.ServiceException;
import com.bsf.wallet.repository.AccountRepository;
import com.bsf.wallet.repository.TransactionRepository;
import com.bsf.wallet.service.TransactionService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionFailLogService transactionFailLogService;

    @Transactional
    @Override
    public void transferMoney(TransferMoneyRequest transferMoneyRequest) {
        try {
            Account crAccount = getAccount(transferMoneyRequest.crAccountId());
            Account drAccount = getAccount(transferMoneyRequest.drAccountId());
            validateBalanceEnough(drAccount, transferMoneyRequest.amount());
            proceedTransfer(crAccount, drAccount, transferMoneyRequest);
        } catch (ServiceException e) {
            saveFailTransaction(transferMoneyRequest, "Custom error", e.getMessage());
        } catch (Exception e) {
            saveFailTransaction(transferMoneyRequest, "System error", e.getMessage());
        }
    }


    /*Private methods*/

    private void proceedTransfer(Account crAccount, Account drAccount, TransferMoneyRequest transferMoneyRequest) {
        /*exchange balance*/
        BigDecimal oldCreditAccountBalance = crAccount.getBalance();
        BigDecimal newCreditAccountBalance = oldCreditAccountBalance.add(transferMoneyRequest.amount());

        BigDecimal oldDebitAccountBalance = drAccount.getBalance();
        BigDecimal newDebitAccountBalance = oldDebitAccountBalance.subtract(transferMoneyRequest.amount());

        crAccount.setBalance(newCreditAccountBalance);
        drAccount.setBalance(newDebitAccountBalance);

        /*create new transaction*/
        Transaction transaction = Transaction.builder()
                .crAccount(crAccount)
                .drAccount(drAccount)
                .description(transferMoneyRequest.description())
                .amount(transferMoneyRequest.amount())
                .crNewBalance(newCreditAccountBalance)
                .drNewBalance(newDebitAccountBalance)
                .reference(transferMoneyRequest.reference())
                .build();

        /*persist data*/
        accountRepository.save(crAccount);
        accountRepository.save(drAccount);
        transactionRepository.save(transaction);
    }

    private Account getAccount(long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ServiceException("No account found for id: " + accountId));
    }

    private void validateBalanceEnough(Account drAccount, BigDecimal amount) {
        if (amount.compareTo(drAccount.getBalance()) >= 0) {
            throw new ServiceException("Not enough balance available for the transaction");
        }
    }

    private void saveFailTransaction(TransferMoneyRequest transferMoneyRequest, String info, String errorMessage) {
        TransactionFailLog transactionFailLog = TransactionFailLog.builder()
                .crAccountId(transferMoneyRequest.crAccountId())
                .drAccountId(transferMoneyRequest.drAccountId())
                .amount(transferMoneyRequest.amount())
                .description(info)
                .error(errorMessage)
                .build();
        transactionFailLogService.saveFailTransaction(transactionFailLog);
    }


}
