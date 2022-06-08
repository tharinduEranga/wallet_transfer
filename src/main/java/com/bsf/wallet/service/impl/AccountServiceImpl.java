package com.bsf.wallet.service.impl;

import com.bsf.wallet.dto.response.AccountDetail;
import com.bsf.wallet.entity.Account;
import com.bsf.wallet.repository.AccountRepository;
import com.bsf.wallet.service.AccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public List<AccountDetail> getAccounts() {
        return getAccountDetails(accountRepository.findAll());
    }

    /*private methods*/
    private List<AccountDetail> getAccountDetails(List<Account> accounts) {
        return accounts
                .stream()
                .map(this::mapAccountDetail)
                .toList();
    }

    private AccountDetail mapAccountDetail(Account account) {
        return new AccountDetail(account.getId(), account.getName(), account.getPassportId(),
                account.getBalance(), account.getCreatedAt());
    }
}
