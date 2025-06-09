package com.lbg.ethereum.DTOs;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ApproveUserTokensForTransferDto {
    private String signer;
    private String tokenAddress;
    private String userAddress;
    private BigInteger amount;

    // Getters and Setters
    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }


}
