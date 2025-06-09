package com.lbg.ethereum.DTOs;

public class TokenDetailsRequestDto {
    private String tokenAddress;
    private String signer;

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }
}
