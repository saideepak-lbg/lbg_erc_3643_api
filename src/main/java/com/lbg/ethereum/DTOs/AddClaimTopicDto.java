package com.lbg.ethereum.DTOs;

public class AddClaimTopicDto {
    private String signer;
    private String claimTopicsRegistryAddress;
    private String topic;

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

    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
}
