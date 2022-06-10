package com.bsf.wallet.service;

import com.bsf.wallet.dto.response.AccountDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public interface AccountService {
    Page<AccountDetail> getAccounts(Pageable pageable);
}
