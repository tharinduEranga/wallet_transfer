package com.bsf.wallet.exception;

import com.bsf.wallet.dto.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by: Tharindu Eranga
 * Date: 08 Jun 2022
 **/
@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<Object> handleCustom(ServiceException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleUnknown(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error!"),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}