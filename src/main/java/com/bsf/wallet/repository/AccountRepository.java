package com.bsf.wallet.repository;

import com.bsf.wallet.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout",value = "5000")})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findById(Long id);
}
