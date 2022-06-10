package com.bsf.wallet.repository;

import com.bsf.wallet.entity.TransactionFailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@Repository
public interface TransactionFailLogRepository extends JpaRepository<TransactionFailLog, Long> {
    List<TransactionFailLog>findByReference(String reference);
}
