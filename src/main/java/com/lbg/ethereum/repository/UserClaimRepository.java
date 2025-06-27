package com.lbg.ethereum.repository;

import com.lbg.ethereum.entities.claims.UserClaim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserClaimRepository extends JpaRepository<UserClaim, Long> {

    // Define custom query methods if needed
    // For example, to find claims by user ID or other criteria
}
