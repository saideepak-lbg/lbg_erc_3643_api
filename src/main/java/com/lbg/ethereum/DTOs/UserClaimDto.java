package com.lbg.ethereum.DTOs;

public class UserClaimDto {
    private String signer;
    private String identityAddress;
    private String topic;

    // Getters and setters
    public String getSigner() {
        return signer;
    }
    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getIdentityAddress() {
        return identityAddress;
    }
    public void setIdentityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
    }

    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
}
