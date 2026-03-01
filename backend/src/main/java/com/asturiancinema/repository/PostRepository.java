package com.asturiancinema.repository;

import com.asturiancinema.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByIsPublishedTrue(Pageable pageable);

    Page<Post> findByCategoryAndIsPublishedTrue(String category, Pageable pageable);

    Optional<Post> findBySlug(String slug);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND " +
           "(LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Post> searchPosts(@Param("query") String query, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true ORDER BY p.publishedAt DESC")
    Page<Post> findLatestPublished(Pageable pageable);

    @Query("SELECT DISTINCT p.category FROM Post p WHERE p.category IS NOT NULL")
    String[] findAllCategories();

    @Query("SELECT COUNT(p) FROM Post p WHERE p.isPublished = true")
    Long countPublishedPosts();

    @Query("SELECT p FROM Post p WHERE p.isPublished = true ORDER BY p.views DESC")
    Page<Post> findMostViewed(Pageable pageable);
}
