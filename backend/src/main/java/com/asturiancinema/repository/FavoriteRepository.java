package com.asturiancinema.repository;

import com.asturiancinema.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUserIdAndMovieId(Long userId, Long movieId);

    boolean existsByUserIdAndMovieId(Long userId, Long movieId);

    void deleteByUserIdAndMovieId(Long userId, Long movieId);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId ORDER BY f.createdAt DESC")
    Page<Favorite> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT f.movie FROM Favorite f WHERE f.user.id = :userId")
    List<com.asturiancinema.entity.Movie> findMoviesByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.movie.id = :movieId")
    Long countByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT f.movie FROM Favorite f WHERE f.user.id = :userId ORDER BY f.createdAt DESC")
    Page<com.asturiancinema.entity.Movie> findMoviesPageByUserId(@Param("userId") Long userId, Pageable pageable);
}
