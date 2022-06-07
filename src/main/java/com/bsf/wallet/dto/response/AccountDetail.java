package com.bsf.wallet.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public record AccountDetail(Long id, String name, String passportId, BigDecimal balance,
                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                            LocalDateTime createdAt) {
}
