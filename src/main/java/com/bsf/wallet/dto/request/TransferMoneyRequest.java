package com.bsf.wallet.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public record TransferMoneyRequest(@NotNull(message = "CR account is mandatory") Long crAccountId,
                                   @NotNull(message = "DR account is mandatory") Long drAccountId,
                                   @Min(1) @NotNull(message = "Amount is mandatory") BigDecimal amount,
                                   String reference,
                                   String description) {
}
