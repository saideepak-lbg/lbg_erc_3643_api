package com.lbg.ethereum.exception.handlers;

public class ClaimException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ClaimException(String message) {
        super(message);

    }

    public ClaimException(String message, Throwable cause) {
        super(message, cause);
    }
}
