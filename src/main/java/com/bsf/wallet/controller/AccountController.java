package com.bsf.wallet.controller;

import java.util.List;
import com.bsf.wallet.dto.response.AccountDetail;
import com.bsf.wallet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bsf.wallet.util.AppConstants.API_VERSION;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@RestController
@RequestMapping(value = "/" + API_VERSION + "/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<AccountDetail>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }
}
