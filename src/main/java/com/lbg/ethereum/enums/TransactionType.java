package com.lbg.ethereum.enums;

public enum TransactionType {
    ON_CHAIN_ID_CREATION("on chain id creation"),

    REGISTER_ON_CHAIN_ID("register on chain id");

    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

}
