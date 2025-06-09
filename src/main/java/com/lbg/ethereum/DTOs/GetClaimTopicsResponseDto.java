package com.lbg.ethereum.DTOs;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public class GetClaimTopicsResponseDto {
    private String message;
    private List<String> claimTopics;

    public String getMessage() {
        return message;
    }
    public void setClaimTopics(List<String> claimTopics) {
        this.claimTopics = claimTopics;
    }
    public List<String> getClaimTopics() {
        return claimTopics;
    }
    public void setMessage(String message) {
        this.message = message;
    }


}
