package com.asturiancinema.repository;

import com.asturiancinema.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.user.id = :userId")
    void revokeAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < CURRENT_TIMESTAMP OR rt.isRevoked = true")
    void deleteExpiredTokens();
}
