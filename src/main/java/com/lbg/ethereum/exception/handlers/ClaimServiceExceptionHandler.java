package com.lbg.ethereum.exception.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice()
public class ClaimServiceExceptionHandler {

    @ExceptionHandler(ClaimException.class)
    public ExceptionResponse handleClaimException(ClaimException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse();
        errorResponse.setStatusCode(500);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath("/claim");
        errorResponse.setTimestamp(Instant.now());
        return errorResponse;
    }
}
