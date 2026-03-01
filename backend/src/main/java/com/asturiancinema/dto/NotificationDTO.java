package com.asturiancinema.dto;

import com.asturiancinema.entity.Notification;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Long id;

    private Notification.NotificationType type;

    private String message;

    private String link;

    private Boolean isRead;

    private UserDTO fromUser;

    private LocalDateTime createdAt;

    public NotificationDTO() {
    }

    public NotificationDTO(Long id, Notification.NotificationType type, String message, String link, Boolean isRead, UserDTO fromUser, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.link = link;
        this.isRead = isRead;
        this.fromUser = fromUser;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Notification.NotificationType getType() {
        return type;
    }

    public void setType(Notification.NotificationType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public UserDTO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDTO fromUser) {
        this.fromUser = fromUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static NotificationDTOBuilder builder() {
        return new NotificationDTOBuilder();
    }

    public static class NotificationDTOBuilder {
        private Long id;
        private Notification.NotificationType type;
        private String message;
        private String link;
        private Boolean isRead;
        private UserDTO fromUser;
        private LocalDateTime createdAt;

        public NotificationDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public NotificationDTOBuilder type(Notification.NotificationType type) {
            this.type = type;
            return this;
        }

        public NotificationDTOBuilder message(String message) {
            this.message = message;
            return this;
        }

        public NotificationDTOBuilder link(String link) {
            this.link = link;
            return this;
        }

        public NotificationDTOBuilder isRead(Boolean isRead) {
            this.isRead = isRead;
            return this;
        }

        public NotificationDTOBuilder fromUser(UserDTO fromUser) {
            this.fromUser = fromUser;
            return this;
        }

        public NotificationDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public NotificationDTO build() {
            return new NotificationDTO(id, type, message, link, isRead, fromUser, createdAt);
        }
    }
}
