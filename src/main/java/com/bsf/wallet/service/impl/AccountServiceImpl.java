package com.bsf.wallet.service.impl;

import com.bsf.wallet.repository.AccountRepository;
import com.bsf.wallet.service.AccountService;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

}
