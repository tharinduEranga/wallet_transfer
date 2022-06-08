package com.bsf.wallet.controller;

import com.bsf.wallet.dto.response.AccountDetail;
import com.bsf.wallet.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bsf.wallet.util.AppConstants.API_VERSION;

/**
 * Created by: Tharindu Eranga
 * Date: 08 Jun 2022
 **/
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/" + API_VERSION + "/transaction")
public class TransactionController {

    private final AccountService accountService;

    @PostMapping(value = "/transfer")
    public ResponseEntity<List<AccountDetail>> transferMoney() {
        log.info("Get account API");
        return ResponseEntity.ok(accountService.getAccounts());
    }
}
