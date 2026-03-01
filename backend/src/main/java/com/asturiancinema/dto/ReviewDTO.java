package com.asturiancinema.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ReviewDTO {

    private Long id;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    private String comment;

    private Boolean isEdited;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDTO user;

    private Long movieId;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, Integer rating, String comment, Boolean isEdited, LocalDateTime createdAt, LocalDateTime updatedAt, UserDTO user, Long movieId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.isEdited = isEdited;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.movieId = movieId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public static ReviewDTOBuilder builder() {
        return new ReviewDTOBuilder();
    }

    public static class ReviewDTOBuilder {
        private Long id;
        private Integer rating;
        private String comment;
        private Boolean isEdited;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private UserDTO user;
        private Long movieId;

        public ReviewDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReviewDTOBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public ReviewDTOBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewDTOBuilder isEdited(Boolean isEdited) {
            this.isEdited = isEdited;
            return this;
        }

        public ReviewDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReviewDTOBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ReviewDTOBuilder user(UserDTO user) {
            this.user = user;
            return this;
        }

        public ReviewDTOBuilder movieId(Long movieId) {
            this.movieId = movieId;
            return this;
        }

        public ReviewDTO build() {
            return new ReviewDTO(id, rating, comment, isEdited, createdAt, updatedAt, user, movieId);
        }
    }
}
