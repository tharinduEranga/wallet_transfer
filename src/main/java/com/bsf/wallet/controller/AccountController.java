package com.bsf.wallet.controller;

import java.util.List;
import com.bsf.wallet.dto.response.AccountDetail;
import com.bsf.wallet.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bsf.wallet.util.AppConstants.API_VERSION;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/" + API_VERSION + "/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping(value = "")
    public ResponseEntity<List<AccountDetail>> getAccounts() {
        log.info("Get account API");
        return ResponseEntity.ok(accountService.getAccounts());
    }
}
