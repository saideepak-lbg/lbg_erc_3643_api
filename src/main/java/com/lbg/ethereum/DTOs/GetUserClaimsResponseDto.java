package com.lbg.ethereum.DTOs;

import java.util.List;

public class GetUserClaimsResponseDto {
    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getReceipt() {
        return receipt;
    }

    public void setReceipt(List<String> receipt) {
        this.receipt = receipt;
    }

    private List<String> receipt;
}
