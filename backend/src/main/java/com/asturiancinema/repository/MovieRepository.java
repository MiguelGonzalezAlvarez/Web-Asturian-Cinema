package com.asturiancinema.repository;

import com.asturiancinema.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByPublishedTrue(Pageable pageable);

    Page<Movie> findByIsAsturianTrueAndPublishedTrue(Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.published = true AND " +
           "(LOWER(m.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(m.director) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Movie> searchMovies(@Param("query") String query, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.published = true AND m.genre = :genre")
    Page<Movie> findByGenre(@Param("genre") String genre, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.published = true AND m.year = :year")
    Page<Movie> findByYear(@Param("year") Integer year, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.published = true AND m.isAsturian = true ORDER BY m.createdAt DESC")
    List<Movie> findLatestAsturianMovies(Pageable pageable);

    @Query("SELECT m FROM Movie m JOIN m.reviews r WHERE m.published = true GROUP BY m ORDER BY AVG(r.rating) DESC")
    Page<Movie> findTopRatedMovies(Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.published = true ORDER BY m.views DESC")
    Page<Movie> findMostViewedMovies(Pageable pageable);

    @Query("SELECT DISTINCT m.year FROM Movie m WHERE m.year IS NOT NULL ORDER BY m.year DESC")
    List<Integer> findAllYears();

    @Query("SELECT DISTINCT m.genre FROM Movie m WHERE m.genre IS NOT NULL")
    List<String> findAllGenres();

    @Query("SELECT COUNT(m) FROM Movie m WHERE m.published = true")
    Long countPublishedMovies();

    @Query("SELECT COUNT(m) FROM Movie m WHERE m.published = true AND m.isAsturian = true")
    Long countAsturianMovies();

    @Query("SELECT m FROM Movie m WHERE m.published = true AND m.latitude IS NOT NULL AND m.longitude IS NOT NULL")
    List<Movie> findMoviesWithLocation();
}
