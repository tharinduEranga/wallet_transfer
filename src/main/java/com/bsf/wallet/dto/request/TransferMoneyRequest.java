package com.bsf.wallet.dto.request;

import java.math.BigDecimal;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public record TransferMoneyRequest(Long crAccountId, Long drAccountId, BigDecimal amount,
                                   String reference, String description) {
}
