package com.lbg.ethereum.DTOs;

import java.math.BigInteger;

public class TransferApprovedTokensDto {
    private String signer;
    private String tokenAddress;
    private String fromAddress;
    private String toAddress;
    private BigInteger transferAmount;

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public BigInteger getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigInteger transferAmount) {
        this.transferAmount = transferAmount;
    }
}
