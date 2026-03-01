package com.asturiancinema.dto;

import java.time.LocalDateTime;

public class PostDTO {

    private Long id;

    private String title;

    private String slug;

    private String excerpt;

    private String content;

    private String coverImage;

    private String category;

    private UserDTO author;

    private LocalDateTime publishedAt;

    private Boolean isPublished;

    private Integer views;

    private Long commentsCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String slug, String excerpt, String content, String coverImage, String category, UserDTO author, LocalDateTime publishedAt, Boolean isPublished, Integer views, Long commentsCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.excerpt = excerpt;
        this.content = content;
        this.coverImage = coverImage;
        this.category = category;
        this.author = author;
        this.publishedAt = publishedAt;
        this.isPublished = isPublished;
        this.views = views;
        this.commentsCount = commentsCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
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

    public static PostDTOBuilder builder() {
        return new PostDTOBuilder();
    }

    public static class PostDTOBuilder {
        private Long id;
        private String title;
        private String slug;
        private String excerpt;
        private String content;
        private String coverImage;
        private String category;
        private UserDTO author;
        private LocalDateTime publishedAt;
        private Boolean isPublished;
        private Integer views;
        private Long commentsCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PostDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostDTOBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostDTOBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public PostDTOBuilder excerpt(String excerpt) {
            this.excerpt = excerpt;
            return this;
        }

        public PostDTOBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostDTOBuilder coverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public PostDTOBuilder category(String category) {
            this.category = category;
            return this;
        }

        public PostDTOBuilder author(UserDTO author) {
            this.author = author;
            return this;
        }

        public PostDTOBuilder publishedAt(LocalDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public PostDTOBuilder isPublished(Boolean isPublished) {
            this.isPublished = isPublished;
            return this;
        }

        public PostDTOBuilder views(Integer views) {
            this.views = views;
            return this;
        }

        public PostDTOBuilder commentsCount(Long commentsCount) {
            this.commentsCount = commentsCount;
            return this;
        }

        public PostDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PostDTOBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PostDTO build() {
            return new PostDTO(id, title, slug, excerpt, content, coverImage, category, author, publishedAt, isPublished, views, commentsCount, createdAt, updatedAt);
        }
    }
}
