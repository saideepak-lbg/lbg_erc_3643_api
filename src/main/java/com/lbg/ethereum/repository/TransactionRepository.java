package com.lbg.ethereum.repository;

import com.lbg.ethereum.entities.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    // Custom query methods can be added here if needed
    // For example, to find transactions by user or status, etc.
}
