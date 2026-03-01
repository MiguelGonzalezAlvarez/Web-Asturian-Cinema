package com.asturiancinema.repository;

import com.asturiancinema.entity.UserActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    @Query("SELECT ua FROM UserActivity ua WHERE ua.user.id = :userId ORDER BY ua.createdAt DESC")
    Page<UserActivity> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT ua FROM UserActivity ua WHERE ua.user.id IN :userIds ORDER BY ua.createdAt DESC")
    Page<UserActivity> findByUserIdIn(@Param("userIds") List<Long> userIds, Pageable pageable);

    @Query("SELECT ua FROM UserActivity ua WHERE ua.targetType = :targetType AND ua.targetId = :targetId ORDER BY ua.createdAt DESC")
    List<UserActivity> findByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);
}
