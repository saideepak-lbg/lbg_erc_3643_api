package com.lbg.ethereum.exception.handlers;

public class OnChainIdCreationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public OnChainIdCreationException(String message) {
        super(message);
    }

    public OnChainIdCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnChainIdCreationException(Throwable cause) {
        super(cause);
    }
}
