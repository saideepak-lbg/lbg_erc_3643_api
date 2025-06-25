package com.lbg.ethereum.repository;

import com.lbg.ethereum.entities.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

   Optional<UserEntity> findByWalletKey(String walletKey);
   Optional<UserEntity> findByUserName(String userName);
}
