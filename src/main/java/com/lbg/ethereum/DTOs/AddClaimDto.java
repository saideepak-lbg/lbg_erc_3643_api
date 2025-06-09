package com.lbg.ethereum.DTOs;

public class AddClaimDto {
    private String identityAddress;
    private String topic;           // The claim topic (string, will be hashed to bytes32)
    private Integer scheme;          // Claim scheme (e.g., ECDSA, etc.)
    private String issuerAddress;          // Issuer address
    private String claimIssuerContractAddress;       // Signature for the claim
    private String data;            // Claim data (hex or base64 string)
    private String uri;
    private String signer;          // Signer address

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

    public Integer getScheme() {
        return scheme;
    }

    public void setScheme(Integer scheme) {
        this.scheme = scheme;
    }

    public String getIssuerAddress() {
        return issuerAddress;
    }

    public void setIssuerAddress(String issuerAddress) {
        this.issuerAddress = issuerAddress;
    }

    public String getClaimIssuerContractAddress() {
        return claimIssuerContractAddress;
    }

    public void setClaimIssuerContractAddress(String claimIssuerContractAddress) {
        this.claimIssuerContractAddress = claimIssuerContractAddress;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
