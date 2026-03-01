package com.asturiancinema.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateReviewRequest {

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    private String comment;

    public CreateReviewRequest() {
    }

    public CreateReviewRequest(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
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

    public static CreateReviewRequestBuilder builder() {
        return new CreateReviewRequestBuilder();
    }

    public static class CreateReviewRequestBuilder {
        private Integer rating;
        private String comment;

        public CreateReviewRequestBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public CreateReviewRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public CreateReviewRequest build() {
            return new CreateReviewRequest(rating, comment);
        }
    }
}
