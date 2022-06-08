package com.bsf.wallet.service;

import com.bsf.wallet.dto.request.TransferMoneyRequest;
import com.bsf.wallet.dto.response.TransferMoneyResponse;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public interface TransactionService {
    TransferMoneyResponse transferMoney(TransferMoneyRequest transferMoneyRequest);
}
