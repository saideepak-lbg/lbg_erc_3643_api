package com.lbg.ethereum.enums;

public enum TransactionType {
    ON_CHAIN_ID_CREATION("on chain id creation"),

    REGISTER_ON_CHAIN_ID("register on chain id"),

    REMOVE_CLAIM_TOPIC("remove claim topic"),
    ADD_CLAIM("add claim"),

    RETRIEVE_CLAIM_TOPIC("retrieve claim topic"),
    APPROVE_TOKENS_FOR_TRANSFER("approve tokens for transfer"),
    TRANSFER_TOKENS("transfer tokens"),
    MINT_TOKEN("mint"),

    ADD_CLAIM_TOPIC("add claim topic"),
    TRANSFER_APPROVED_TOKENS("transfer approved tokens"),
    FREEZE_TOKENS("freeze Tokens"),
    UNFREEZE_TOKENS("unfreeze_tokens"),
    PAUSE_TOKEN("pause token"),
    UNPAUSE_TOKEN("unpause token"),
    BURN_TOKENS("burn tokens"),
    FREEZE_ACCOUNT("freeze account"),
    UNFREEZE_ACCOUNT("unfreeze_account"),
    RECOVER_ACCOUNT("recover account");

    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

}
