package com.lbg.ethereum.DTOs;

public class AddkeyDto {
    private String identityAddress; // Identity contract address
    private String key;             // The key to add (usually keccak256(abi.encode(address)))
    private int purpose;            // e.g., 3 for CLAIM, 1 for MANAGEMENT
    private int keyType;

    public String getIdentityAddress() {
        return identityAddress;
    }

    public void setIdentityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPurpose() {
        return purpose;
    }

    public void setPurpose(int purpose) {
        this.purpose = purpose;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }
}
