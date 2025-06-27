package com.lbg.ethereum.exception.handlers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice()
public class UserServiceExceptionHandler {

    /**
     * Handles OnChainIdCreationException and returns a custom error response.
     *
     * @param ex the exception to handle
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(OnChainIdCreationException.class)
    public ResponseEntity<ExceptionResponse> handleOnChainIdCreationException(OnChainIdCreationException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse();
        errorResponse.setStatusCode(500);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath("/user/create-onchain-identity");
        errorResponse.setTimestamp(Instant.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles RegisterOnChainIdException and returns a custom error response.
     *
     * @param ex the exception to handle
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(RegisterOnChainIdException.class)
    public ResponseEntity<ExceptionResponse> handleRegisterOnChainIdException(RegisterOnChainIdException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse();
        errorResponse.setStatusCode(500);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath("/user/register-onchain-identity");
        errorResponse.setTimestamp(Instant.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
