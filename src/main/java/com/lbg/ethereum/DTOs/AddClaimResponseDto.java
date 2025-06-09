package com.lbg.ethereum.DTOs;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class AddClaimResponseDto {
    private int statusCode;
    private String message;
    private TransactionReceipt receipt;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransactionReceipt getReceipt() {
        return receipt;
    }

    public void setReceipt(TransactionReceipt receipt) {
        this.receipt = receipt;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
