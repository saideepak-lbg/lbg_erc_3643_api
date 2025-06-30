package com.lbg.ethereum.repository;

import com.lbg.ethereum.entities.claims.UserClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserClaimRepository extends JpaRepository<UserClaim, Long> {

    // Define custom query methods if needed
    // For example, to find claims by user ID or other criteria
    @Query(nativeQuery = true,value = "SELECT * FROM user_claim WHERE user_identity = :userIdentity AND claim_topic_name = :claimTopicName")
    UserClaim findByUserIdentityAndClaimTopicName(String userIdentity, String claimTopicName);

}
