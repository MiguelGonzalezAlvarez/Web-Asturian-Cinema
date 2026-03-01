package com.asturiancinema.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post_comments")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private PostComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> replies = new ArrayList<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public PostComment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public PostComment getParent() { return parent; }
    public void setParent(PostComment parent) { this.parent = parent; }

    public List<PostComment> getReplies() { return replies; }
    public void setReplies(List<PostComment> replies) { this.replies = replies; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static class PostCommentBuilder {
        private Long id;
        private Post post;
        private User user;
        private PostComment parent;
        private List<PostComment> replies = new ArrayList<>();
        private String content;
        private LocalDateTime createdAt;

        public PostCommentBuilder id(Long id) { this.id = id; return this; }
        public PostCommentBuilder post(Post post) { this.post = post; return this; }
        public PostCommentBuilder user(User user) { this.user = user; return this; }
        public PostCommentBuilder parent(PostComment parent) { this.parent = parent; return this; }
        public PostCommentBuilder replies(List<PostComment> replies) { this.replies = replies; return this; }
        public PostCommentBuilder content(String content) { this.content = content; return this; }
        public PostCommentBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public PostComment build() {
            PostComment postComment = new PostComment();
            postComment.id = this.id;
            postComment.post = this.post;
            postComment.user = this.user;
            postComment.parent = this.parent;
            postComment.replies = this.replies;
            postComment.content = this.content;
            postComment.createdAt = this.createdAt;
            return postComment;
        }
    }

    public static PostCommentBuilder builder() {
        return new PostCommentBuilder();
    }
}
