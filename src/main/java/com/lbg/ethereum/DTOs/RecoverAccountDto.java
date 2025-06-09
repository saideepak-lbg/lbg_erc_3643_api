package com.lbg.ethereum.DTOs;

public class RecoverAccountDto {
    private String signer;
    private String lostWalletAddress;
    private String newWalletAddress;
    private String userIdentity; // Optional

    // Getters and Setters
    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getLostWalletAddress() {
        return lostWalletAddress;
    }

    public void setLostWalletAddress(String lostWalletAddress) {
        this.lostWalletAddress = lostWalletAddress;
    }

    public String getNewWalletAddress() {
        return newWalletAddress;
    }

    public void setNewWalletAddress(String newWalletAddress) {
        this.newWalletAddress = newWalletAddress;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }
}
