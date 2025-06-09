package com.lbg.ethereum.DTOs;

public class RemoveClaimTopicDto {
    private String signer;
    private String claimTopicsRegistryAddress;
    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getClaimTopicsRegistryAddress() {
        return claimTopicsRegistryAddress;
    }

    public void setClaimTopicsRegistryAddress(String claimTopicsRegistryAddress) {
        this.claimTopicsRegistryAddress = claimTopicsRegistryAddress;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }
}
