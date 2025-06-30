package com.lbg.ethereum.exception.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice()
public class TokenServiceExceptionHandler {

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ExceptionResponse> tokenExceptionHandler(TokenException tokenException) {
        ExceptionResponse errorResponse = new ExceptionResponse();
        tokenException.printStackTrace();
        errorResponse.setStatusCode(500);
        errorResponse.setMessage(tokenException.getMessage());
        errorResponse.setPath("/token");
        errorResponse.setTimestamp(Instant.now());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
