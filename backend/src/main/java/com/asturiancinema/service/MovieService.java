package com.asturiancinema.service;

import com.asturiancinema.dto.*;
import com.asturiancinema.entity.*;
import com.asturiancinema.exception.BadRequestException;
import com.asturiancinema.exception.ResourceNotFoundException;
import com.asturiancinema.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public MovieService(MovieRepository movieRepository, ReviewRepository reviewRepository, FavoriteRepository favoriteRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    public PageResponse<MovieDTO> getAllMovies(Pageable pageable) {
        Page<Movie> movies = movieRepository.findByPublishedTrue(pageable);
        return PageResponse.from(movies.map(this::mapToDTO));
    }

    public PageResponse<MovieDTO> getAsturianMovies(Pageable pageable) {
        Page<Movie> movies = movieRepository.findByIsAsturianTrueAndPublishedTrue(pageable);
        return PageResponse.from(movies.map(this::mapToDTO));
    }

    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        
        movie.setViews(movie.getViews() + 1);
        movieRepository.save(movie);
        
        return mapToDTO(movie);
    }

    public PageResponse<MovieDTO> searchMovies(String query, Pageable pageable) {
        Page<Movie> movies = movieRepository.searchMovies(query, pageable);
        return PageResponse.from(movies.map(this::mapToDTO));
    }

    public PageResponse<MovieDTO> getMoviesByGenre(String genre, Pageable pageable) {
        Page<Movie> movies = movieRepository.findByGenre(genre, pageable);
        return PageResponse.from(movies.map(this::mapToDTO));
    }

    public PageResponse<MovieDTO> getTopRatedMovies(Pageable pageable) {
        Page<Movie> movies = movieRepository.findTopRatedMovies(pageable);
        return PageResponse.from(movies.map(this::mapToDTO));
    }

    public List<Integer> getAllYears() {
        return movieRepository.findAllYears();
    }

    public List<String> getAllGenres() {
        return movieRepository.findAllGenres();
    }

    @Transactional
    public MovieDTO createMovie(CreateMovieRequest request) {
        User currentUser = getCurrentUser();
        
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .originalTitle(request.getOriginalTitle())
                .synopsis(request.getSynopsis())
                .director(request.getDirector())
                .year(request.getYear())
                .duration(request.getDuration())
                .genre(request.getGenre())
                .coverImage(request.getCoverImage())
                .backdropImage(request.getBackdropImage())
                .trailerUrl(request.getTrailerUrl())
                .movieUrl(request.getMovieUrl())
                .isAsturian(request.getIsAsturian() != null ? request.getIsAsturian() : false)
                .origin(request.getOrigin())
                .language(request.getLanguage() != null ? request.getLanguage() : "ast")
                .cast(request.getCast())
                .productionCompany(request.getProductionCompany())
                .filmingLocations(request.getFilmingLocations())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .published(request.getPublished() != null ? request.getPublished() : false)
                .createdBy(currentUser)
                .views(0)
                .build();

        Movie saved = movieRepository.save(movie);
        return mapToDTO(saved);
    }

    @Transactional
    public MovieDTO updateMovie(Long id, CreateMovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movie.setTitle(request.getTitle());
        movie.setOriginalTitle(request.getOriginalTitle());
        movie.setSynopsis(request.getSynopsis());
        movie.setDirector(request.getDirector());
        movie.setYear(request.getYear());
        movie.setDuration(request.getDuration());
        movie.setGenre(request.getGenre());
        movie.setCoverImage(request.getCoverImage());
        movie.setBackdropImage(request.getBackdropImage());
        movie.setTrailerUrl(request.getTrailerUrl());
        movie.setMovieUrl(request.getMovieUrl());
        movie.setIsAsturian(request.getIsAsturian());
        movie.setOrigin(request.getOrigin());
        movie.setLanguage(request.getLanguage());
        movie.setCast(request.getCast());
        movie.setProductionCompany(request.getProductionCompany());
        movie.setFilmingLocations(request.getFilmingLocations());
        movie.setLatitude(request.getLatitude());
        movie.setLongitude(request.getLongitude());
        movie.setPublished(request.getPublished());

        Movie saved = movieRepository.save(movie);
        return mapToDTO(saved);
    }

    @Transactional
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie not found");
        }
        movieRepository.deleteById(id);
    }

    public List<Movie> getMoviesWithLocation() {
        return movieRepository.findMoviesWithLocation();
    }

    private MovieDTO mapToDTO(Movie movie) {
        User currentUser = getCurrentUserOpt();
        boolean isFavorite = false;
        
        if (currentUser != null) {
            isFavorite = favoriteRepository.existsByUserIdAndMovieId(currentUser.getId(), movie.getId());
        }

        Double averageRating = reviewRepository.getAverageRatingByMovieId(movie.getId());
        Long reviewCount = reviewRepository.countByMovieId(movie.getId());

        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .originalTitle(movie.getOriginalTitle())
                .synopsis(movie.getSynopsis())
                .director(movie.getDirector())
                .year(movie.getYear())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .coverImage(movie.getCoverImage())
                .backdropImage(movie.getBackdropImage())
                .trailerUrl(movie.getTrailerUrl())
                .movieUrl(movie.getMovieUrl())
                .isAsturian(movie.getIsAsturian())
                .origin(movie.getOrigin())
                .language(movie.getLanguage())
                .cast(movie.getCast())
                .productionCompany(movie.getProductionCompany())
                .filmingLocations(movie.getFilmingLocations())
                .latitude(movie.getLatitude())
                .longitude(movie.getLongitude())
                .published(movie.getPublished())
                .averageRating(averageRating != null ? averageRating : 0.0)
                .reviewCount(reviewCount != null ? reviewCount.intValue() : 0)
                .views(movie.getViews())
                .createdAt(movie.getCreatedAt())
                .isFavorite(isFavorite)
                .build();
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private User getCurrentUserOpt() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByUsername(username).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
