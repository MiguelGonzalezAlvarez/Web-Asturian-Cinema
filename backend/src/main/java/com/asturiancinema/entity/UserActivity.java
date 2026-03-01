package com.asturiancinema.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activities")
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false, length = 50)
    private ActivityType activityType;

    @Column(name = "target_type", length = 50)
    private String targetType;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "target_title", length = 200)
    private String targetTitle;

    @Column(name = "target_image", length = 500)
    private String targetImage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public UserActivity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }

    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }

    public String getTargetTitle() { return targetTitle; }
    public void setTargetTitle(String targetTitle) { this.targetTitle = targetTitle; }

    public String getTargetImage() { return targetImage; }
    public void setTargetImage(String targetImage) { this.targetImage = targetImage; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public enum ActivityType {
        CREATED_REVIEW,
        ADDED_FAVORITE,
        FOLLOWED_USER,
        CREATED_LIST,
        PUBLISHED_POST,
        COMMENTED
    }

    public static class UserActivityBuilder {
        private Long id;
        private User user;
        private ActivityType activityType;
        private String targetType;
        private Long targetId;
        private String targetTitle;
        private String targetImage;
        private LocalDateTime createdAt;

        public UserActivityBuilder id(Long id) { this.id = id; return this; }
        public UserActivityBuilder user(User user) { this.user = user; return this; }
        public UserActivityBuilder activityType(ActivityType activityType) { this.activityType = activityType; return this; }
        public UserActivityBuilder targetType(String targetType) { this.targetType = targetType; return this; }
        public UserActivityBuilder targetId(Long targetId) { this.targetId = targetId; return this; }
        public UserActivityBuilder targetTitle(String targetTitle) { this.targetTitle = targetTitle; return this; }
        public UserActivityBuilder targetImage(String targetImage) { this.targetImage = targetImage; return this; }
        public UserActivityBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserActivity build() {
            UserActivity userActivity = new UserActivity();
            userActivity.id = this.id;
            userActivity.user = this.user;
            userActivity.activityType = this.activityType;
            userActivity.targetType = this.targetType;
            userActivity.targetId = this.targetId;
            userActivity.targetTitle = this.targetTitle;
            userActivity.targetImage = this.targetImage;
            userActivity.createdAt = this.createdAt;
            return userActivity;
        }
    }

    public static UserActivityBuilder builder() {
        return new UserActivityBuilder();
    }
}
