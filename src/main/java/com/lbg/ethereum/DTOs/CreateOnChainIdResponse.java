package com.lbg.ethereum.DTOs;

public class CreateOnChainIdResponse {
    private String address; // The deployed contract address
    private Object tx;      // The transaction/deployment object (could be a web3j TransactionReceipt or Contract)

    // Getters and setters
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Object getTx() {
        return tx;
    }
    public void setTx(Object tx) {
        this.tx = tx;
    }
}
