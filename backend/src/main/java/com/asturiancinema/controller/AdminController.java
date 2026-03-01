package com.asturiancinema.controller;

import com.asturiancinema.dto.*;
import com.asturiancinema.entity.*;
import com.asturiancinema.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final RoleRepository roleRepository;

    public AdminController(UserRepository userRepository, MovieRepository movieRepository, 
                          ReviewRepository reviewRepository, PostRepository postRepository, 
                          RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.postRepository = postRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalUsers", userRepository.count());
        stats.put("activeUsers", userRepository.countActiveUsers());
        stats.put("totalMovies", movieRepository.count());
        stats.put("publishedMovies", movieRepository.countPublishedMovies());
        stats.put("asturianMovies", movieRepository.countAsturianMovies());
        stats.put("totalReviews", reviewRepository.countAllReviews());
        stats.put("publishedPosts", postRepository.countPublishedPosts());

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/users")
    public ResponseEntity<PageResponse<UserDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        
        Page<UserDTO> userDTOs = users.map(this::mapToUserDTO);
        
        PageResponse<UserDTO> response = PageResponse.from(userDTOs);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String roleName = request.get("role");
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Role role = roleRepository.findByName(Role.RoleName.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Role not found"));
        
        user.getRoles().clear();
        user.addRole(role);
        
        User saved = userRepository.save(user);
        return ResponseEntity.ok(mapToUserDTO(saved));
    }

    @PutMapping("/users/{id}/active")
    public ResponseEntity<UserDTO> toggleUserActive(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setIsActive(!user.getIsActive());
        User saved = userRepository.save(user);
        
        return ResponseEntity.ok(mapToUserDTO(saved));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movies/all")
    public ResponseEntity<PageResponse<MovieDTO>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Boolean published) {
        
        Page<Movie> movies;
        if (published != null) {
            movies = movieRepository.findByPublishedTrue(PageRequest.of(page, size));
        } else {
            movies = movieRepository.findAll(PageRequest.of(page, size));
        }
        
        Page<MovieDTO> movieDTOs = movies.map(this::mapToMovieDTO);
        PageResponse<MovieDTO> response = PageResponse.from(movieDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/reported")
    public ResponseEntity<PageResponse<ReviewDTO>> getReportedReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Page<Review> reviews = reviewRepository.findAll(PageRequest.of(page, size));
        Page<ReviewDTO> reviewDTOs = reviews.map(this::mapToReviewDTO);
        PageResponse<ReviewDTO> response = PageResponse.from(reviewDTOs);
        return ResponseEntity.ok(response);
    }

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .roles(user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet()))
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private MovieDTO mapToMovieDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .published(movie.getPublished())
                .createdAt(movie.getCreatedAt())
                .build();
    }

    private ReviewDTO mapToReviewDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
