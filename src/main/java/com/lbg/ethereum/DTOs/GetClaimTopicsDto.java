package com.lbg.ethereum.DTOs;

public class GetClaimTopicsDto {
    private String signer;
    private String claimTopicsRegistryAddress;

    // Getters and Setters
    public String getSigner() {
        return signer;
    }
    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getClaimTopicsRegistryAddress() {
        return claimTopicsRegistryAddress;
    }
    public void setClaimTopicsRegistryAddress(String claimTopicsRegistryAddress) {
        this.claimTopicsRegistryAddress = claimTopicsRegistryAddress;
    }
}
