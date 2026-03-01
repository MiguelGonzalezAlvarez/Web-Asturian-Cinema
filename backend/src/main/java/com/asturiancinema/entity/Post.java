package com.asturiancinema.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 200)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String excerpt;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "cover_image", length = 500)
    private String coverImage;

    @Column(length = 50)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column
    private Boolean isPublished = false;

    @Column
    private Integer views = 0;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Post() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }

    public Boolean getIsPublished() { return isPublished; }
    public void setIsPublished(Boolean isPublished) { this.isPublished = isPublished; }

    public Integer getViews() { return views; }
    public void setViews(Integer views) { this.views = views; }

    public List<PostComment> getComments() { return comments; }
    public void setComments(List<PostComment> comments) { this.comments = comments; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static class PostBuilder {
        private Long id;
        private String title;
        private String slug;
        private String excerpt;
        private String content;
        private String coverImage;
        private String category;
        private User author;
        private LocalDateTime publishedAt;
        private Boolean isPublished = false;
        private Integer views = 0;
        private List<PostComment> comments = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PostBuilder id(Long id) { this.id = id; return this; }
        public PostBuilder title(String title) { this.title = title; return this; }
        public PostBuilder slug(String slug) { this.slug = slug; return this; }
        public PostBuilder excerpt(String excerpt) { this.excerpt = excerpt; return this; }
        public PostBuilder content(String content) { this.content = content; return this; }
        public PostBuilder coverImage(String coverImage) { this.coverImage = coverImage; return this; }
        public PostBuilder category(String category) { this.category = category; return this; }
        public PostBuilder author(User author) { this.author = author; return this; }
        public PostBuilder publishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; return this; }
        public PostBuilder isPublished(Boolean isPublished) { this.isPublished = isPublished; return this; }
        public PostBuilder views(Integer views) { this.views = views; return this; }
        public PostBuilder comments(List<PostComment> comments) { this.comments = comments; return this; }
        public PostBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public PostBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Post build() {
            Post post = new Post();
            post.id = this.id;
            post.title = this.title;
            post.slug = this.slug;
            post.excerpt = this.excerpt;
            post.content = this.content;
            post.coverImage = this.coverImage;
            post.category = this.category;
            post.author = this.author;
            post.publishedAt = this.publishedAt;
            post.isPublished = this.isPublished;
            post.views = this.views;
            post.comments = this.comments;
            post.createdAt = this.createdAt;
            post.updatedAt = this.updatedAt;
            return post;
        }
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }
}
