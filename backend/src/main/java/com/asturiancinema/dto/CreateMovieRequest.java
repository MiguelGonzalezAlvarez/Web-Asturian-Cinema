package com.asturiancinema.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateMovieRequest {

    @NotBlank(message = "Title is required")
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

    public CreateMovieRequest() {
    }

    public CreateMovieRequest(String title, String originalTitle, String synopsis, String director, Integer year, Integer duration, String genre, String coverImage, String backdropImage, String trailerUrl, String movieUrl, Boolean isAsturian, String origin, String language, String cast, String productionCompany, String filmingLocations, Double latitude, Double longitude, Boolean published) {
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

    public static CreateMovieRequestBuilder builder() {
        return new CreateMovieRequestBuilder();
    }

    public static class CreateMovieRequestBuilder {
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

        public CreateMovieRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CreateMovieRequestBuilder originalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public CreateMovieRequestBuilder synopsis(String synopsis) {
            this.synopsis = synopsis;
            return this;
        }

        public CreateMovieRequestBuilder director(String director) {
            this.director = director;
            return this;
        }

        public CreateMovieRequestBuilder year(Integer year) {
            this.year = year;
            return this;
        }

        public CreateMovieRequestBuilder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public CreateMovieRequestBuilder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public CreateMovieRequestBuilder coverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public CreateMovieRequestBuilder backdropImage(String backdropImage) {
            this.backdropImage = backdropImage;
            return this;
        }

        public CreateMovieRequestBuilder trailerUrl(String trailerUrl) {
            this.trailerUrl = trailerUrl;
            return this;
        }

        public CreateMovieRequestBuilder movieUrl(String movieUrl) {
            this.movieUrl = movieUrl;
            return this;
        }

        public CreateMovieRequestBuilder isAsturian(Boolean isAsturian) {
            this.isAsturian = isAsturian;
            return this;
        }

        public CreateMovieRequestBuilder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public CreateMovieRequestBuilder language(String language) {
            this.language = language;
            return this;
        }

        public CreateMovieRequestBuilder cast(String cast) {
            this.cast = cast;
            return this;
        }

        public CreateMovieRequestBuilder productionCompany(String productionCompany) {
            this.productionCompany = productionCompany;
            return this;
        }

        public CreateMovieRequestBuilder filmingLocations(String filmingLocations) {
            this.filmingLocations = filmingLocations;
            return this;
        }

        public CreateMovieRequestBuilder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public CreateMovieRequestBuilder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public CreateMovieRequestBuilder published(Boolean published) {
            this.published = published;
            return this;
        }

        public CreateMovieRequest build() {
            return new CreateMovieRequest(title, originalTitle, synopsis, director, year, duration, genre, coverImage, backdropImage, trailerUrl, movieUrl, isAsturian, origin, language, cast, productionCompany, filmingLocations, latitude, longitude, published);
        }
    }
}
