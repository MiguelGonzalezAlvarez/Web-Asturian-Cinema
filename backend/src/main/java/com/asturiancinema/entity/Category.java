package com.asturiancinema.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(name = "icon_url", length = 500)
    private String iconUrl;

    @OneToMany(mappedBy = "category")
    private List<Movie> movies = new ArrayList<>();

    public Category() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public List<Movie> getMovies() { return movies; }
    public void setMovies(List<Movie> movies) { this.movies = movies; }

    public static class CategoryBuilder {
        private Long id;
        private String name;
        private String description;
        private String iconUrl;
        private List<Movie> movies = new ArrayList<>();

        public CategoryBuilder id(Long id) { this.id = id; return this; }
        public CategoryBuilder name(String name) { this.name = name; return this; }
        public CategoryBuilder description(String description) { this.description = description; return this; }
        public CategoryBuilder iconUrl(String iconUrl) { this.iconUrl = iconUrl; return this; }
        public CategoryBuilder movies(List<Movie> movies) { this.movies = movies; return this; }

        public Category build() {
            Category category = new Category();
            category.id = this.id;
            category.name = this.name;
            category.description = this.description;
            category.iconUrl = this.iconUrl;
            category.movies = this.movies;
            return category;
        }
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }
}
