package com.asturiancinema.service;

import com.asturiancinema.dto.*;
import com.asturiancinema.entity.*;
import com.asturiancinema.exception.BadRequestException;
import com.asturiancinema.exception.ResourceNotFoundException;
import com.asturiancinema.repository.*;
import com.asturiancinema.security.CustomUserDetailsService;
import com.asturiancinema.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .isActive(true)
                .build();

        Role userRole = roleRepository.findByName(Role.RoleName.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.addRole(userRole);

        User savedUser = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
        String accessToken = jwtUtils.generateToken(userDetails);
        String refreshToken = createRefreshToken(savedUser);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getJwtExpiration())
                .user(mapToUserDTO(savedUser))
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetailsService.CustomUserDetails userDetails = (CustomUserDetailsService.CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtUtils.generateToken(userDetails);
        String refreshToken = createRefreshToken(userDetails.getUser());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getJwtExpiration())
                .user(mapToUserDTO(userDetails.getUser()))
                .build();
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        if (refreshToken.isExpired() || refreshToken.getIsRevoked()) {
            throw new BadRequestException("Refresh token expired or revoked");
        }

        User user = refreshToken.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String accessToken = jwtUtils.generateToken(userDetails);

        String newRefreshToken = createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getJwtExpiration())
                .user(mapToUserDTO(user))
                .build();
    }

    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToUserDTO(user);
    }

    private String createRefreshToken(User user) {
        refreshTokenRepository.revokeAllByUserId(user.getId());

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .isRevoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .roles(user.getRoles().stream().map(r -> r.getName().name()).collect(java.util.stream.Collectors.toSet()))
                .isActive(user.getIsActive())
                .followersCount((long) user.getFollowers().size())
                .followingCount((long) user.getFollowing().size())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
