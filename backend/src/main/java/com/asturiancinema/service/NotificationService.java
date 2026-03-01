package com.asturiancinema.service;

import com.asturiancinema.entity.*;
import com.asturiancinema.repository.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void createReviewNotification(Review review) {
        User movieOwner = review.getMovie().getCreatedBy();
        if (movieOwner != null && !movieOwner.getId().equals(review.getUser().getId())) {
            Notification notification = Notification.builder()
                    .user(movieOwner)
                    .type(Notification.NotificationType.NEW_REVIEW)
                    .message(review.getUser().getUsername() + " ha valorado tu película " + review.getMovie().getTitle())
                    .link("/movie/" + review.getMovie().getId())
                    .fromUser(review.getUser())
                    .relatedMovie(review.getMovie())
                    .build();
            
            notificationRepository.save(notification);
            sendNotification(movieOwner.getId(), notification);
        }
    }

    public void createFollowNotification(User follower, User followee) {
        Notification notification = Notification.builder()
                .user(followee)
                .type(Notification.NotificationType.NEW_FOLLOW)
                .message(follower.getUsername() + " ha comenzado a seguirte")
                .link("/profile/" + follower.getId())
                .fromUser(follower)
                .build();

        notificationRepository.save(notification);
        sendNotification(followee.getId(), notification);
    }

    public void createCommentNotification(PostComment comment) {
        User postOwner = comment.getPost().getAuthor();
        if (postOwner != null && !postOwner.getId().equals(comment.getUser().getId())) {
            Notification notification = Notification.builder()
                    .user(postOwner)
                    .type(Notification.NotificationType.NEW_COMMENT)
                    .message(comment.getUser().getUsername() + " ha comentado en tu post")
                    .link("/blog/" + comment.getPost().getSlug())
                    .fromUser(comment.getUser())
                    .relatedPost(comment.getPost())
                    .build();

            notificationRepository.save(notification);
            sendNotification(postOwner.getId(), notification);
        }
    }

    private void sendNotification(Long userId, Notification notification) {
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                notification
        );
    }
}
