package com.bsf.wallet.config;

import com.bsf.wallet.repository.AccountRepository;
import com.bsf.wallet.repository.TransactionRepository;
import com.bsf.wallet.service.AccountService;
import com.bsf.wallet.service.TransactionService;
import com.bsf.wallet.service.impl.AccountServiceImpl;
import com.bsf.wallet.service.impl.TransactionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@Configuration
public class ServiceConfig {
    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountServiceImpl(accountRepository);
    }
    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository) {
        return new TransactionServiceImpl(transactionRepository);
    }
}
