package com.asturiancinema.controller;

import com.asturiancinema.dto.*;
import com.asturiancinema.service.MovieService;
import com.asturiancinema.service.ReviewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    public MovieController(MovieService movieService, ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<MovieDTO>> getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc") 
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        
        return ResponseEntity.ok(movieService.getAllMovies(pageable));
    }

    @GetMapping("/asturianas")
    public ResponseEntity<PageResponse<MovieDTO>> getAsturianMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(movieService.getAsturianMovies(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<MovieDTO>> searchMovies(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(movieService.searchMovies(q, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping("/genres")
    public ResponseEntity<List<String>> getGenres() {
        return ResponseEntity.ok(movieService.getAllGenres());
    }

    @GetMapping("/years")
    public ResponseEntity<List<Integer>> getYears() {
        return ResponseEntity.ok(movieService.getAllYears());
    }

    @GetMapping("/top-rated")
    public ResponseEntity<PageResponse<MovieDTO>> getTopRatedMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(movieService.getTopRatedMovies(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody CreateMovieRequest request) {
        return ResponseEntity.ok(movieService.createMovie(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody CreateMovieRequest request) {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<PageResponse<ReviewDTO>> getMovieReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(reviewService.getMovieReviews(id, pageable));
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewDTO> createReview(
            @PathVariable Long id,
            @RequestBody CreateReviewRequest request) {
        return ResponseEntity.ok(reviewService.createReview(id, request));
    }

    @GetMapping("/map/locations")
    public ResponseEntity<List<MovieDTO>> getMoviesWithLocation() {
        return ResponseEntity.ok(movieService.getMoviesWithLocation().stream()
                .map(m -> movieService.getMovieById(m.getId()))
                .toList());
    }
}
