package com.asturiancinema.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class UserDTO {

    private Long id;

    private String username;

    private String email;

    private String fullName;

    private String avatarUrl;

    private String bio;

    private Set<String> roles;

    private Boolean isActive;

    private Long followersCount;

    private Long followingCount;

    private Long moviesCount;

    private LocalDateTime createdAt;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, String fullName, String avatarUrl, String bio, Set<String> roles, Boolean isActive, Long followersCount, Long followingCount, Long moviesCount, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.roles = roles;
        this.isActive = isActive;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.moviesCount = moviesCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Long followersCount) {
        this.followersCount = followersCount;
    }

    public Long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Long followingCount) {
        this.followingCount = followingCount;
    }

    public Long getMoviesCount() {
        return moviesCount;
    }

    public void setMoviesCount(Long moviesCount) {
        this.moviesCount = moviesCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public static class UserDTOBuilder {
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private String avatarUrl;
        private String bio;
        private Set<String> roles;
        private Boolean isActive;
        private Long followersCount;
        private Long followingCount;
        private Long moviesCount;
        private LocalDateTime createdAt;

        public UserDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserDTOBuilder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public UserDTOBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public UserDTOBuilder roles(Set<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserDTOBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public UserDTOBuilder followersCount(Long followersCount) {
            this.followersCount = followersCount;
            return this;
        }

        public UserDTOBuilder followingCount(Long followingCount) {
            this.followingCount = followingCount;
            return this;
        }

        public UserDTOBuilder moviesCount(Long moviesCount) {
            this.moviesCount = moviesCount;
            return this;
        }

        public UserDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(id, username, email, fullName, avatarUrl, bio, roles, isActive, followersCount, followingCount, moviesCount, createdAt);
        }
    }
}
