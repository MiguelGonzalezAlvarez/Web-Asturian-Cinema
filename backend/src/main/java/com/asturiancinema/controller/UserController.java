package com.asturiancinema.controller;

import com.asturiancinema.dto.*;
import com.asturiancinema.entity.*;
import com.asturiancinema.repository.*;
import com.asturiancinema.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    public UserController(UserRepository userRepository, FavoriteRepository favoriteRepository,
                        ReviewRepository reviewRepository, NotificationRepository notificationRepository,
                        NotificationService notificationService) {
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
        this.reviewRepository = reviewRepository;
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() {
        User user = getCurrentUser();
        return ResponseEntity.ok(mapToUserDTO(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody Map<String, String> updates) {
        User user = getCurrentUser();
        
        if (updates.containsKey("fullName")) user.setFullName(updates.get("fullName"));
        if (updates.containsKey("bio")) user.setBio(updates.get("bio"));
        if (updates.containsKey("avatarUrl")) user.setAvatarUrl(updates.get("avatarUrl"));
        
        User saved = userRepository.save(user);
        return ResponseEntity.ok(mapToUserDTO(saved));
    }

    @GetMapping("/favorites")
    public ResponseEntity<PageResponse<MovieDTO>> getFavorites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        User user = getCurrentUser();
        Page<Movie> favorites = favoriteRepository.findMoviesPageByUserId(user.getId(), PageRequest.of(page, size));
        
        Page<MovieDTO> favoriteDTOs = favorites.map(this::mapToMovieDTO);
        PageResponse<MovieDTO> response = PageResponse.from(favoriteDTOs);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/favorites/{movieId}")
    public ResponseEntity<Void> addToFavorites(@PathVariable Long movieId) {
        User user = getCurrentUser();
        
        if (!favoriteRepository.existsByUserIdAndMovieId(user.getId(), movieId)) {
            Movie movie = new Movie();
            movie.setId(movieId);
            Favorite favorite = Favorite.builder()
                    .user(user)
                    .movie(movie)
                    .build();
            favoriteRepository.save(favorite);
        }
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/favorites/{movieId}")
    public ResponseEntity<Void> removeFromFavorites(@PathVariable Long movieId) {
        User user = getCurrentUser();
        favoriteRepository.deleteByUserIdAndMovieId(user.getId(), movieId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<Void> followUser(@PathVariable Long userId) {
        User currentUser = getCurrentUser();
        User userToFollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        currentUser.getFollowing().add(userToFollow);
        userRepository.save(currentUser);
        
        notificationService.createFollowNotification(currentUser, userToFollow);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/follow/{userId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long userId) {
        User currentUser = getCurrentUser();
        User userToUnfollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        currentUser.getFollowing().remove(userToUnfollow);
        userRepository.save(currentUser);
        
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(mapToUserDTO(user));
    }

    @GetMapping("/notifications")
    public ResponseEntity<PageResponse<NotificationDTO>> getNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        User user = getCurrentUser();
        Page<Notification> notifications = notificationRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId(), PageRequest.of(page, size));
        
        Page<NotificationDTO> notificationDTOs = notifications.map(this::mapToNotificationDTO);
        PageResponse<NotificationDTO> response = PageResponse.from(notificationDTOs);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/notifications/read-all")
    public ResponseEntity<Void> markAllNotificationsAsRead() {
        User user = getCurrentUser();
        notificationRepository.markAllAsRead(user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notifications/unread-count")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        User user = getCurrentUser();
        Long count = notificationRepository.countUnreadByUserId(user.getId());
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .roles(user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet()))
                .isActive(user.getIsActive())
                .followersCount((long) user.getFollowers().size())
                .followingCount((long) user.getFollowing().size())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private MovieDTO mapToMovieDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .coverImage(movie.getCoverImage())
                .year(movie.getYear())
                .build();
    }

    private NotificationDTO mapToNotificationDTO(Notification notification) {
        UserDTO fromUser = null;
        if (notification.getFromUser() != null) {
            fromUser = UserDTO.builder()
                    .id(notification.getFromUser().getId())
                    .username(notification.getFromUser().getUsername())
                    .avatarUrl(notification.getFromUser().getAvatarUrl())
                    .build();
        }

        return NotificationDTO.builder()
                .id(notification.getId())
                .type(notification.getType())
                .message(notification.getMessage())
                .link(notification.getLink())
                .isRead(notification.getIsRead())
                .fromUser(fromUser)
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
