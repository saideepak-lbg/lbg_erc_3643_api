package com.lbg.ethereum.DTOs;

public class OnChainIdCreationDto {
    private String signer;
    private String userAddress;
    private String identityImplementationAuthority;


    public String getSigner() {
        return signer;
    }
    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getUserAddress() {
        return userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getIdentityImplementationAuthority() {
        return identityImplementationAuthority;
    }
    public void setIdentityImplementationAuthority(String identityImplementationAuthority) {
        this.identityImplementationAuthority = identityImplementationAuthority;
    }
}