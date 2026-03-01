package com.asturiancinema.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie_lists")
public class MovieList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "cover_image", length = 500)
    private String coverImage;

    @Column
    private Boolean isPublic = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "movie_list_movies",
        joinColumns = @JoinColumn(name = "list_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> movies = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public MovieList() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public Boolean getIsPublic() { return isPublic; }
    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Set<Movie> getMovies() { return movies; }
    public void setMovies(Set<Movie> movies) { this.movies = movies; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static class MovieListBuilder {
        private Long id;
        private String name;
        private String description;
        private String coverImage;
        private Boolean isPublic = true;
        private User user;
        private Set<Movie> movies = new HashSet<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public MovieListBuilder id(Long id) { this.id = id; return this; }
        public MovieListBuilder name(String name) { this.name = name; return this; }
        public MovieListBuilder description(String description) { this.description = description; return this; }
        public MovieListBuilder coverImage(String coverImage) { this.coverImage = coverImage; return this; }
        public MovieListBuilder isPublic(Boolean isPublic) { this.isPublic = isPublic; return this; }
        public MovieListBuilder user(User user) { this.user = user; return this; }
        public MovieListBuilder movies(Set<Movie> movies) { this.movies = movies; return this; }
        public MovieListBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public MovieListBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public MovieList build() {
            MovieList movieList = new MovieList();
            movieList.id = this.id;
            movieList.name = this.name;
            movieList.description = this.description;
            movieList.coverImage = this.coverImage;
            movieList.isPublic = this.isPublic;
            movieList.user = this.user;
            movieList.movies = this.movies;
            movieList.createdAt = this.createdAt;
            movieList.updatedAt = this.updatedAt;
            return movieList;
        }
    }

    public static MovieListBuilder builder() {
        return new MovieListBuilder();
    }
}
