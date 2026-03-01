package com.asturiancinema.dto;

import java.time.LocalDateTime;

public class MovieDTO {

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

    private Boolean isAsturian;

    private String origin;

    private String language;

    private String cast;

    private String productionCompany;

    private String filmingLocations;

    private Double latitude;

    private Double longitude;

    private Boolean published;

    private Double averageRating;

    private Integer reviewCount;

    private Integer views;

    private LocalDateTime createdAt;

    private UserDTO createdBy;

    private Boolean isFavorite;

    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String originalTitle, String synopsis, String director, Integer year, Integer duration, String genre, String coverImage, String backdropImage, String trailerUrl, String movieUrl, Boolean isAsturian, String origin, String language, String cast, String productionCompany, String filmingLocations, Double latitude, Double longitude, Boolean published, Double averageRating, Integer reviewCount, Integer views, LocalDateTime createdAt, UserDTO createdBy, Boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.synopsis = synopsis;
        this.director = director;
        this.year = year;
        this.duration = duration;
        this.genre = genre;
        this.coverImage = coverImage;
        this.backdropImage = backdropImage;
        this.trailerUrl = trailerUrl;
        this.movieUrl = movieUrl;
        this.isAsturian = isAsturian;
        this.origin = origin;
        this.language = language;
        this.cast = cast;
        this.productionCompany = productionCompany;
        this.filmingLocations = filmingLocations;
        this.latitude = latitude;
        this.longitude = longitude;
        this.published = published;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.views = views;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.isFavorite = isFavorite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getBackdropImage() {
        return backdropImage;
    }

    public void setBackdropImage(String backdropImage) {
        this.backdropImage = backdropImage;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public Boolean getIsAsturian() {
        return isAsturian;
    }

    public void setIsAsturian(Boolean isAsturian) {
        this.isAsturian = isAsturian;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getFilmingLocations() {
        return filmingLocations;
    }

    public void setFilmingLocations(String filmingLocations) {
        this.filmingLocations = filmingLocations;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public static MovieDTOBuilder builder() {
        return new MovieDTOBuilder();
    }

    public static class MovieDTOBuilder {
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
        private Boolean isAsturian;
        private String origin;
        private String language;
        private String cast;
        private String productionCompany;
        private String filmingLocations;
        private Double latitude;
        private Double longitude;
        private Boolean published;
        private Double averageRating;
        private Integer reviewCount;
        private Integer views;
        private LocalDateTime createdAt;
        private UserDTO createdBy;
        private Boolean isFavorite;

        public MovieDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MovieDTOBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MovieDTOBuilder originalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public MovieDTOBuilder synopsis(String synopsis) {
            this.synopsis = synopsis;
            return this;
        }

        public MovieDTOBuilder director(String director) {
            this.director = director;
            return this;
        }

        public MovieDTOBuilder year(Integer year) {
            this.year = year;
            return this;
        }

        public MovieDTOBuilder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public MovieDTOBuilder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public MovieDTOBuilder coverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public MovieDTOBuilder backdropImage(String backdropImage) {
            this.backdropImage = backdropImage;
            return this;
        }

        public MovieDTOBuilder trailerUrl(String trailerUrl) {
            this.trailerUrl = trailerUrl;
            return this;
        }

        public MovieDTOBuilder movieUrl(String movieUrl) {
            this.movieUrl = movieUrl;
            return this;
        }

        public MovieDTOBuilder isAsturian(Boolean isAsturian) {
            this.isAsturian = isAsturian;
            return this;
        }

        public MovieDTOBuilder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public MovieDTOBuilder language(String language) {
            this.language = language;
            return this;
        }

        public MovieDTOBuilder cast(String cast) {
            this.cast = cast;
            return this;
        }

        public MovieDTOBuilder productionCompany(String productionCompany) {
            this.productionCompany = productionCompany;
            return this;
        }

        public MovieDTOBuilder filmingLocations(String filmingLocations) {
            this.filmingLocations = filmingLocations;
            return this;
        }

        public MovieDTOBuilder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public MovieDTOBuilder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public MovieDTOBuilder published(Boolean published) {
            this.published = published;
            return this;
        }

        public MovieDTOBuilder averageRating(Double averageRating) {
            this.averageRating = averageRating;
            return this;
        }

        public MovieDTOBuilder reviewCount(Integer reviewCount) {
            this.reviewCount = reviewCount;
            return this;
        }

        public MovieDTOBuilder views(Integer views) {
            this.views = views;
            return this;
        }

        public MovieDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MovieDTOBuilder createdBy(UserDTO createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public MovieDTOBuilder isFavorite(Boolean isFavorite) {
            this.isFavorite = isFavorite;
            return this;
        }

        public MovieDTO build() {
            return new MovieDTO(id, title, originalTitle, synopsis, director, year, duration, genre, coverImage, backdropImage, trailerUrl, movieUrl, isAsturian, origin, language, cast, productionCompany, filmingLocations, latitude, longitude, published, averageRating, reviewCount, views, createdAt, createdBy, isFavorite);
        }
    }
}
