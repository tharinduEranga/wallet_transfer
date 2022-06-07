package com.bsf.wallet.repository;

import com.bsf.wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
