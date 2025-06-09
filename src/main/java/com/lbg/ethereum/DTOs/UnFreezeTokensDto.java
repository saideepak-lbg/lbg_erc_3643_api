package com.lbg.ethereum.DTOs;

import java.math.BigInteger;

public class UnFreezeTokensDto {
    private String signer;
    private String tokenAddress;
    private String userAddress;
    private BigInteger unFreezeAmount;

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

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public BigInteger getUnFreezeAmount() {
        return unFreezeAmount;
    }

    public void setUnFreezeAmount(BigInteger unFreezeAmount) {
        this.unFreezeAmount = unFreezeAmount;
    }
}
