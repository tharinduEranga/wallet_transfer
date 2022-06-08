package com.bsf.wallet.exception;

import lombok.Getter;

/**
 * Created by: Tharindu Eranga
 * Date: 08 Jun 2022
 **/
@Getter
public class ServiceException extends RuntimeException {
    private final String message;

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }
}
