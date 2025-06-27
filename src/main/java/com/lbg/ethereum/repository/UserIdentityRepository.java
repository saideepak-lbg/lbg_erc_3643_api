package com.lbg.ethereum.repository;

import com.lbg.ethereum.entities.users.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {


    @Query(value = "SELECT * FROM user_identity WHERE identity_address = :identityAddress AND user_id = :userId", nativeQuery = true)
    Optional<UserIdentity> findByIdentityAddressAndUserId(String identityAddress, Long userId);
}
