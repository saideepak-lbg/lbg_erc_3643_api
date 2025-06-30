package com.lbg.ethereum.repository;

import com.lbg.ethereum.entities.Tokens.TokenEntity;
import com.lbg.ethereum.entities.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByUserId(Long userId);
}
