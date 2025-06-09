package com.lbg.ethereum.DTOs;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class GetUserTokenResponseDto {
    private String message;
    private TransactionReceipt transactionReceipt;
    private String balance;
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

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }
}
