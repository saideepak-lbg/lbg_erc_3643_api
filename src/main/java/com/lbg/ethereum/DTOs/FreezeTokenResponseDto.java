package com.lbg.ethereum.DTOs;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class FreezeTokenResponseDto {
    private String message;
    private TransactionReceipt transactionReceipt;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransactionReceipt getTransactionReceipt() {
        return transactionReceipt;
    }

    public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }
}
