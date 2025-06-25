package com.lbg.ethereum.exception.handlers;

public class RegisterOnChainIdException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RegisterOnChainIdException(String message) {
        super(message);
    }

    public RegisterOnChainIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterOnChainIdException(Throwable cause) {
        super(cause);
    }
}
