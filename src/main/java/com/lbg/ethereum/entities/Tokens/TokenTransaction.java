package com.lbg.ethereum.entities.Tokens;

import jakarta.persistence.*;

@Entity
@Table(name = "token_transaction", schema = "ethereum")
public class TokenTransaction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long tokenTransactionId;


        @Column(nullable = false)
        private Long tokenId;

        @Column(nullable = false, length = 250)
        private String transactionHash;

    public Long getTokenTransactionId() {
        return tokenTransactionId;
    }

    public void setTokenTransactionId(Long tokenTransactionId) {
        this.tokenTransactionId = tokenTransactionId;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
}
