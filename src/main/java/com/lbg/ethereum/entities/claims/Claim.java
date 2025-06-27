package com.lbg.ethereum.entities.claims;


import com.lbg.ethereum.entities.users.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "claim")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private Long claimId;

    @ManyToOne
    @JoinColumn(name = "signer_id", referencedColumnName = "user_id")
    private UserEntity signer;

    @Column(name = "topic_name", length = 100, nullable = false)
    private String topicName;

    // Getters and setters

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public UserEntity getSigner() {
        return signer;
    }

    public void setSigner(UserEntity signer) {
        this.signer = signer;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
