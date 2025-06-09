package com.lbg.ethereum.DTOs;

public class RegisterIdentityDto {
    private String signer;
    private String identityRegistryAddress;
    private String userAddress;
    private String userIdentity;
    private int countryCode;

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getIdentityRegistryAddress() {
        return identityRegistryAddress;
    }

    public void setIdentityRegistryAddress(String identityRegistryAddress) {
        this.identityRegistryAddress = identityRegistryAddress;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }
}
