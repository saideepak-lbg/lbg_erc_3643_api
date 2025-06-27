package com.lbg.ethereum.enums;

public enum TransactionType {
    ON_CHAIN_ID_CREATION("on chain id creation"),

    REGISTER_ON_CHAIN_ID("register on chain id"),

    REMOVE_CLAIM_TOPIC("remove claim topic"),
    ADD_CLAIM("add claim"),

    RETRIEVE_CLAIM_TOPIC("retrieve claim topic"),

    ADD_CLAIM_TOPIC("add claim topic");

    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

}
