package com.lbg.ethereum.repository;

import com.lbg.ethereum.entities.Tokens.TokenTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenTransactionRepository extends JpaRepository<TokenTransaction, Long> {

    // Custom query methods can be added here if needed
    // For example, to find transactions by token address, user, or status, etc.
}
