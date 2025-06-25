package com.lbg.ethereum.repository;

import com.lbg.ethereum.entities.users.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
    /**
     * Finds a UserIdentity by its identity address.
     *
     * @param identityAddress the identity address to search for
     * @return an Optional containing the UserIdentity if found, or empty if not found
     */
    Optional<UserIdentity> findByIdentityAddress(String identityAddress);
}
