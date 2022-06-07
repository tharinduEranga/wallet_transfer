package com.bsf.wallet.service;

import com.bsf.wallet.dto.response.AccountDetail;

import java.util.List;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public interface AccountService {
    List<AccountDetail> getAccounts();
}
