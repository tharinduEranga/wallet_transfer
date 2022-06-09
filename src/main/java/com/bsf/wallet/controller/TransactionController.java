package com.bsf.wallet.controller;

import com.bsf.wallet.dto.request.TransferMoneyRequest;
import com.bsf.wallet.dto.response.TransferMoneyResponse;
import com.bsf.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

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

    private final TransactionService transactionService;

    @PostMapping(value = "/transfer")
    public ResponseEntity<TransferMoneyResponse> transferMoney(
            @Valid @RequestBody TransferMoneyRequest transferMoneyRequest) {

        log.info("Transfer money API -> request: {}", transferMoneyRequest);

        TransferMoneyResponse transferMoneyResponse = transactionService
                .transferMoney(transferMoneyRequest);

        log.info("Transfer money Success -> response: {}", transferMoneyResponse);
        return ResponseEntity.ok(transferMoneyResponse);
    }
}
