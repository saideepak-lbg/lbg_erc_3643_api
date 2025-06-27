package com.lbg.ethereum.entities.claims;

import com.lbg.ethereum.entities.transactions.Transaction;
import com.lbg.ethereum.entities.users.UserIdentity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_claim")
public class UserClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_claim_id")
    private Long userClaimId;


    @Column(name = "identity_address")
    private String userIdentity;

    @ManyToOne
    @JoinColumn(name = "transaction_hash", referencedColumnName = "transaction_hash")
    private Transaction transaction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "claim_id", referencedColumnName = "claim_id")
    private Claim claim;

    @Column(name = "scheme")
    private Integer scheme;

    @Column(name = "data", columnDefinition = "text")
    private String data;

    @Column(name = "claim_added_date")
    private LocalDateTime claimAddedDate;

    // Getters and setters

    public Long getUserClaimId() {
        return userClaimId;
    }

    public void setUserClaimId(Long userClaimId) {
        this.userClaimId = userClaimId;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public Integer getScheme() {
        return scheme;
    }

    public void setScheme(Integer scheme) {
        this.scheme = scheme;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getClaimAddedDate() {
        return claimAddedDate;
    }

    public void setClaimAddedDate(LocalDateTime claimAddedDate) {
        this.claimAddedDate = claimAddedDate;
    }
}