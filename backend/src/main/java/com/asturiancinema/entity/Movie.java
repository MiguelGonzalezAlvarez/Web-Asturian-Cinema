package com.asturiancinema.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "original_title", length = 200)
    private String originalTitle;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    @Column(length = 100)
    private String director;

    @Column
    private Integer year;

    @Column
    private Integer duration;

    @Column(length = 50)
    private String genre;

    @Column(name = "cover_image", length = 500)
    private String coverImage;

    @Column(name = "backdrop_image", length = 500)
    private String backdropImage;

    @Column(name = "trailer_url", length = 500)
    private String trailerUrl;

    @Column(name = "movie_url", length = 500)
    private String movieUrl;

    @Column(name = "is_asturian")
    private Boolean isAsturian = false;

    @Column(length = 100)
    private String origin;

    @Column(length = 50)
    private String language = "ast";

    @Column(length = 200)
    private String cast;

    @Column(name = "production_company", length = 200)
    private String productionCompany;

    @Column(name = "filming_locations", length = 500)
    private String filmingLocations;

    @Column
    private Integer views = 0;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column
    private Boolean published = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Movie() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOriginalTitle() { return originalTitle; }
    public void setOriginalTitle(String originalTitle) { this.originalTitle = originalTitle; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public String getBackdropImage() { return backdropImage; }
    public void setBackdropImage(String backdropImage) { this.backdropImage = backdropImage; }

    public String getTrailerUrl() { return trailerUrl; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }

    public String getMovieUrl() { return movieUrl; }
    public void setMovieUrl(String movieUrl) { this.movieUrl = movieUrl; }

    public Boolean getIsAsturian() { return isAsturian; }
    public void setIsAsturian(Boolean isAsturian) { this.isAsturian = isAsturian; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getCast() { return cast; }
    public void setCast(String cast) { this.cast = cast; }

    public String getProductionCompany() { return productionCompany; }
    public void setProductionCompany(String productionCompany) { this.productionCompany = productionCompany; }

    public String getFilmingLocations() { return filmingLocations; }
    public void setFilmingLocations(String filmingLocations) { this.filmingLocations = filmingLocations; }

    public Integer getViews() { return views; }
    public void setViews(Integer views) { this.views = views; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Boolean getPublished() { return published; }
    public void setPublished(Boolean published) { this.published = published; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public List<Favorite> getFavorites() { return favorites; }
    public void setFavorites(List<Favorite> favorites) { this.favorites = favorites; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Double getAverageRating() {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream()
            .mapToInt(Review::getRating)
            .average()
            .orElse(0.0);
    }

    public Integer getReviewCount() {
        return reviews != null ? reviews.size() : 0;
    }

    public static class MovieBuilder {
        private Long id;
        private String title;
        private String originalTitle;
        private String synopsis;
        private String director;
        private Integer year;
        private Integer duration;
        private String genre;
        private String coverImage;
        private String backdropImage;
        private String trailerUrl;
        private String movieUrl;
        private Boolean isAsturian = false;
        private String origin;
        private String language = "ast";
        private String cast;
        private String productionCompany;
        private String filmingLocations;
        private Integer views = 0;
        private Double latitude;
        private Double longitude;
        private Boolean published = false;
        private User createdBy;
        private List<Review> reviews = new ArrayList<>();
        private List<Favorite> favorites = new ArrayList<>();
        private Category category;

        public MovieBuilder id(Long id) { this.id = id; return this; }
        public MovieBuilder title(String title) { this.title = title; return this; }
        public MovieBuilder originalTitle(String originalTitle) { this.originalTitle = originalTitle; return this; }
        public MovieBuilder synopsis(String synopsis) { this.synopsis = synopsis; return this; }
        public MovieBuilder director(String director) { this.director = director; return this; }
        public MovieBuilder year(Integer year) { this.year = year; return this; }
        public MovieBuilder duration(Integer duration) { this.duration = duration; return this; }
        public MovieBuilder genre(String genre) { this.genre = genre; return this; }
        public MovieBuilder coverImage(String coverImage) { this.coverImage = coverImage; return this; }
        public MovieBuilder backdropImage(String backdropImage) { this.backdropImage = backdropImage; return this; }
        public MovieBuilder trailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; return this; }
        public MovieBuilder movieUrl(String movieUrl) { this.movieUrl = movieUrl; return this; }
        public MovieBuilder isAsturian(Boolean isAsturian) { this.isAsturian = isAsturian; return this; }
        public MovieBuilder origin(String origin) { this.origin = origin; return this; }
        public MovieBuilder language(String language) { this.language = language; return this; }
        public MovieBuilder cast(String cast) { this.cast = cast; return this; }
        public MovieBuilder productionCompany(String productionCompany) { this.productionCompany = productionCompany; return this; }
        public MovieBuilder filmingLocations(String filmingLocations) { this.filmingLocations = filmingLocations; return this; }
        public MovieBuilder views(Integer views) { this.views = views; return this; }
        public MovieBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
        public MovieBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
        public MovieBuilder published(Boolean published) { this.published = published; return this; }
        public MovieBuilder createdBy(User createdBy) { this.createdBy = createdBy; return this; }
        public MovieBuilder reviews(List<Review> reviews) { this.reviews = reviews; return this; }
        public MovieBuilder favorites(List<Favorite> favorites) { this.favorites = favorites; return this; }
        public MovieBuilder category(Category category) { this.category = category; return this; }

        public Movie build() {
            Movie movie = new Movie();
            movie.id = this.id;
            movie.title = this.title;
            movie.originalTitle = this.originalTitle;
            movie.synopsis = this.synopsis;
            movie.director = this.director;
            movie.year = this.year;
            movie.duration = this.duration;
            movie.genre = this.genre;
            movie.coverImage = this.coverImage;
            movie.backdropImage = this.backdropImage;
            movie.trailerUrl = this.trailerUrl;
            movie.movieUrl = this.movieUrl;
            movie.isAsturian = this.isAsturian;
            movie.origin = this.origin;
            movie.language = this.language;
            movie.cast = this.cast;
            movie.productionCompany = this.productionCompany;
            movie.filmingLocations = this.filmingLocations;
            movie.views = this.views;
            movie.latitude = this.latitude;
            movie.longitude = this.longitude;
            movie.published = this.published;
            movie.createdBy = this.createdBy;
            movie.reviews = this.reviews;
            movie.favorites = this.favorites;
            movie.category = this.category;
            return movie;
        }
    }

    public static MovieBuilder builder() {
        return new MovieBuilder();
    }
}
