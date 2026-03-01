package com.asturiancinema.repository;

import com.asturiancinema.entity.MovieList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieListRepository extends JpaRepository<MovieList, Long> {

    Page<MovieList> findByUserId(Long userId, Pageable pageable);

    Page<MovieList> findByUserIdAndIsPublicTrue(Long userId, Pageable pageable);

    @Query("SELECT ml FROM MovieList ml WHERE ml.isPublic = true ORDER BY ml.createdAt DESC")
    Page<MovieList> findAllPublic(Pageable pageable);

    Optional<MovieList> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT ml FROM MovieList ml WHERE ml.id = :id AND (ml.isPublic = true OR ml.user.id = :userId)")
    Optional<MovieList> findByIdAndUserIdOrPublic(@Param("id") Long id, @Param("userId") Long userId);
}
