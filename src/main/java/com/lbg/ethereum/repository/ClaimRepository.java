package com.lbg.ethereum.repository;

import com.lbg.ethereum.entities.claims.Claim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim,Long> {

    @Query(value = "SELECT topic_name FROM claim WHERE signer_id = :signerId", nativeQuery = true)
    List<String> findBySignerId(Long signerId);

    @Query(value="Select * from claim where topic_name = :topicName", nativeQuery = true)
    Claim findByTopicName(@Param("topicName") String topicName);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM claim WHERE topic_name = :topicName AND signer_id = :signerId", nativeQuery = true)
    void deleteByTopicNameAndSignerId(@Param("topicName") String topicName, @Param("signerId") Long signerId);
}
