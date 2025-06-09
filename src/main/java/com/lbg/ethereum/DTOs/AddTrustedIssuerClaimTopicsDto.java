package com.lbg.ethereum.DTOs;

import java.util.List;

public class AddTrustedIssuerClaimTopicsDto {
    private String signer;
    private String claimIssuerContractAddress; // Optional
    private String trustedIssuersRegistryContractAddress; // Optional
    private List<String> topics;

    // Getters and Setters
    public String getSigner() {
        return signer;
    }
    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getClaimIssuerContractAddress() {
        return claimIssuerContractAddress;
    }
    public void setClaimIssuerContractAddress(String claimIssuerContractAddress) {
        this.claimIssuerContractAddress = claimIssuerContractAddress;
    }

    public String getTrustedIssuersRegistryContractAddress() {
        return trustedIssuersRegistryContractAddress;
    }
    public void setTrustedIssuersRegistryContractAddress(String trustedIssuersRegistryContractAddress) {
        this.trustedIssuersRegistryContractAddress = trustedIssuersRegistryContractAddress;
    }

    public List<String> getTopics() {
        return topics;
    }
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
