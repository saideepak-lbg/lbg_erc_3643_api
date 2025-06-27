package com.lbg.ethereum.entities.users;


import com.lbg.ethereum.entities.transactions.Transaction;
import jakarta.persistence.*;

@Entity
@Table(name = "user_identity")
public class UserIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_identity_id")
    private Long userIdentityId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "identity_address", length = 100, nullable = false)
    private String identityAddress;

    @ManyToOne
    @JoinColumn(name = "transaction_hash")
    private Transaction transaction;

    @Column(name = "signer_id")
    private Long signerId;

    @Column(name = "is_registered", nullable = false)
    private Boolean isRegistered = false;

    // Getters and Setters


    public Boolean getRegistered() {
        return isRegistered;
    }

    public void setRegistered(Boolean registered) {
        isRegistered = registered;
    }

    public Long getUserIdentityId() {
        return userIdentityId;
    }

    public void setUserIdentityId(Long userIdentityId) {
        this.userIdentityId = userIdentityId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getIdentityAddress() {
        return identityAddress;
    }

    public void setIdentityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Long getSignerId() {
        return signerId;
    }

    public void setSignerId(Long signerId) {
        this.signerId = signerId;
    }
}