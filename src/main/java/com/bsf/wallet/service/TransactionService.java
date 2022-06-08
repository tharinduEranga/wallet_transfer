package com.bsf.wallet.service;

import com.bsf.wallet.dto.request.TransferMoneyRequest;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public interface TransactionService {
    void transferMoney(TransferMoneyRequest transferMoneyRequest);
}
