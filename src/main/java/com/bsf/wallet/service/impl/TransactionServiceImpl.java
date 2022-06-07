package com.bsf.wallet.service.impl;

import com.bsf.wallet.repository.TransactionRepository;
import com.bsf.wallet.service.TransactionService;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
