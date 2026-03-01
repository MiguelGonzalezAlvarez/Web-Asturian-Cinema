package com.asturiancinema.service;

import com.asturiancinema.dto.*;
import com.asturiancinema.entity.*;
import com.asturiancinema.exception.BadRequestException;
import com.asturiancinema.exception.ResourceNotFoundException;
import com.asturiancinema.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository, UserRepository userRepository, NotificationService notificationService) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public PageResponse<ReviewDTO> getMovieReviews(Long movieId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByMovieId(movieId, pageable);
        return PageResponse.from(reviews.map(this::mapToDTO));
    }

    @Transactional
    public ReviewDTO createReview(Long movieId, CreateReviewRequest request) {
        User user = getCurrentUser();
        
        if (reviewRepository.existsByMovieIdAndUserId(movieId, user.getId())) {
            throw new BadRequestException("You have already reviewed this movie");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Review review = Review.builder()
                .user(user)
                .movie(movie)
                .rating(request.getRating())
                .comment(request.getComment())
                .isEdited(false)
                .build();

        Review saved = reviewRepository.save(review);
        
        notificationService.createReviewNotification(saved);
        
        return mapToDTO(saved);
    }

    @Transactional
    public ReviewDTO updateReview(Long reviewId, CreateReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        User currentUser = getCurrentUser();
        if (!review.getUser().getId().equals(currentUser.getId()) && !isAdmin()) {
            throw new BadRequestException("You can only edit your own reviews");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setIsEdited(true);

        Review saved = reviewRepository.save(review);
        return mapToDTO(saved);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        User currentUser = getCurrentUser();
        if (!review.getUser().getId().equals(currentUser.getId()) && !isAdmin()) {
            throw new BadRequestException("You can only delete your own reviews");
        }

        reviewRepository.delete(review);
    }

    private ReviewDTO mapToDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .isEdited(review.getIsEdited())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .movieId(review.getMovie().getId())
                .user(UserDTO.builder()
                        .id(review.getUser().getId())
                        .username(review.getUser().getUsername())
                        .avatarUrl(review.getUser().getAvatarUrl())
                        .build())
                .build();
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
