package com.asturiancinema.repository;

import com.asturiancinema.entity.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    @Query("SELECT pc FROM PostComment pc WHERE pc.post.id = :postId AND pc.parent IS NULL ORDER BY pc.createdAt DESC")
    Page<PostComment> findRootCommentsByPostId(@Param("postId") Long postId, Pageable pageable);

    @Query("SELECT COUNT(pc) FROM PostComment pc WHERE pc.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);
}
