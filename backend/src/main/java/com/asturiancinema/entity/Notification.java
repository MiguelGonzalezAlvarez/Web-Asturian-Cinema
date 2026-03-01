package com.asturiancinema.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationType type;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(length = 200)
    private String link;

    @Column
    private Boolean isRead = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie relatedMovie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post relatedPost;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Notification() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public User getFromUser() { return fromUser; }
    public void setFromUser(User fromUser) { this.fromUser = fromUser; }

    public Movie getRelatedMovie() { return relatedMovie; }
    public void setRelatedMovie(Movie relatedMovie) { this.relatedMovie = relatedMovie; }

    public Post getRelatedPost() { return relatedPost; }
    public void setRelatedPost(Post relatedPost) { this.relatedPost = relatedPost; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public enum NotificationType {
        NEW_COMMENT,
        NEW_REPLY,
        NEW_FOLLOW,
        NEW_REVIEW,
        POST_PUBLISHED,
        SYSTEM
    }

    public static class NotificationBuilder {
        private Long id;
        private User user;
        private NotificationType type;
        private String message;
        private String link;
        private Boolean isRead = false;
        private User fromUser;
        private Movie relatedMovie;
        private Post relatedPost;
        private LocalDateTime createdAt;

        public NotificationBuilder id(Long id) { this.id = id; return this; }
        public NotificationBuilder user(User user) { this.user = user; return this; }
        public NotificationBuilder type(NotificationType type) { this.type = type; return this; }
        public NotificationBuilder message(String message) { this.message = message; return this; }
        public NotificationBuilder link(String link) { this.link = link; return this; }
        public NotificationBuilder isRead(Boolean isRead) { this.isRead = isRead; return this; }
        public NotificationBuilder fromUser(User fromUser) { this.fromUser = fromUser; return this; }
        public NotificationBuilder relatedMovie(Movie relatedMovie) { this.relatedMovie = relatedMovie; return this; }
        public NotificationBuilder relatedPost(Post relatedPost) { this.relatedPost = relatedPost; return this; }
        public NotificationBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Notification build() {
            Notification notification = new Notification();
            notification.id = this.id;
            notification.user = this.user;
            notification.type = this.type;
            notification.message = this.message;
            notification.link = this.link;
            notification.isRead = this.isRead;
            notification.fromUser = this.fromUser;
            notification.relatedMovie = this.relatedMovie;
            notification.relatedPost = this.relatedPost;
            notification.createdAt = this.createdAt;
            return notification;
        }
    }

    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }
}
