package com.bsf.wallet.service.impl;

import com.bsf.wallet.entity.TransactionFailLog;
import com.bsf.wallet.repository.TransactionFailLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: Tharindu Eranga
 * Date: 08 Jun 2022
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionFailLogService {
    private final TransactionFailLogRepository transactionFailLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailTransaction(TransactionFailLog transactionFailLog) {
        transactionFailLogRepository.save(transactionFailLog);
    }
}
