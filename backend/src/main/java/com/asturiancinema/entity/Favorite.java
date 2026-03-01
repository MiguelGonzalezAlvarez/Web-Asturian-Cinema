package com.asturiancinema.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "movie_id"})
})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Favorite() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static class FavoriteBuilder {
        private Long id;
        private User user;
        private Movie movie;
        private LocalDateTime createdAt;

        public FavoriteBuilder id(Long id) { this.id = id; return this; }
        public FavoriteBuilder user(User user) { this.user = user; return this; }
        public FavoriteBuilder movie(Movie movie) { this.movie = movie; return this; }
        public FavoriteBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Favorite build() {
            Favorite favorite = new Favorite();
            favorite.id = this.id;
            favorite.user = this.user;
            favorite.movie = this.movie;
            favorite.createdAt = this.createdAt;
            return favorite;
        }
    }

    public static FavoriteBuilder builder() {
        return new FavoriteBuilder();
    }
}
