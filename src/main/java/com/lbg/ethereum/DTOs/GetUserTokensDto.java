package com.lbg.ethereum.DTOs;

public class GetUserTokensDto {
    private String signer;
    private String tokenAddress;
    private String userAddress;

    // Getters and Setters
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
}
