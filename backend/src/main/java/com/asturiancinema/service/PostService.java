package com.asturiancinema.service;

import com.asturiancinema.dto.PageResponse;
import com.asturiancinema.dto.PostDTO;
import com.asturiancinema.entity.Post;
import com.asturiancinema.entity.User;
import com.asturiancinema.exception.ResourceNotFoundException;
import com.asturiancinema.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PageResponse<PostDTO> getPublishedPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findByIsPublishedTrue(pageable);
        return PageResponse.from(posts.map(this::mapToDTO));
    }

    public PostDTO getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
        
        return mapToDTO(post);
    }

    public PageResponse<PostDTO> getPostsByCategory(String category, Pageable pageable) {
        Page<Post> posts = postRepository.findByCategoryAndIsPublishedTrue(category, pageable);
        return PageResponse.from(posts.map(this::mapToDTO));
    }

    public PageResponse<PostDTO> searchPosts(String query, Pageable pageable) {
        Page<Post> posts = postRepository.searchPosts(query, pageable);
        return PageResponse.from(posts.map(this::mapToDTO));
    }

    @Transactional
    public PostDTO createPost(PostDTO postDTO) {
        User currentUser = getCurrentUser();
        
        String slug = postDTO.getTitle().toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
        
        Post post = Post.builder()
                .title(postDTO.getTitle())
                .slug(slug)
                .excerpt(postDTO.getExcerpt())
                .content(postDTO.getContent())
                .coverImage(postDTO.getCoverImage())
                .category(postDTO.getCategory())
                .author(currentUser)
                .isPublished(postDTO.getIsPublished() != null && postDTO.getIsPublished())
                .publishedAt(postDTO.getIsPublished() != null && postDTO.getIsPublished() ? LocalDateTime.now() : null)
                .views(0)
                .build();

        Post saved = postRepository.save(post);
        return mapToDTO(saved);
    }

    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setTitle(postDTO.getTitle());
        post.setExcerpt(postDTO.getExcerpt());
        post.setContent(postDTO.getContent());
        post.setCoverImage(postDTO.getCoverImage());
        post.setCategory(postDTO.getCategory());
        
        if (postDTO.getIsPublished() != null && postDTO.getIsPublished() && !post.getIsPublished()) {
            post.setPublishedAt(LocalDateTime.now());
        }
        post.setIsPublished(postDTO.getIsPublished() != null ? postDTO.getIsPublished() : false);

        Post saved = postRepository.save(post);
        return mapToDTO(saved);
    }

    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found");
        }
        postRepository.deleteById(id);
    }

    private PostDTO mapToDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .slug(post.getSlug())
                .excerpt(post.getExcerpt())
                .content(post.getContent())
                .coverImage(post.getCoverImage())
                .category(post.getCategory())
                .publishedAt(post.getPublishedAt())
                .isPublished(post.getIsPublished())
                .views(post.getViews())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return postRepository.findAll().stream()
                .filter(u -> u.getAuthor() != null && u.getAuthor().getUsername().equals(username))
                .findFirst()
                .map(Post::getAuthor)
                .orElse(null);
    }
}
