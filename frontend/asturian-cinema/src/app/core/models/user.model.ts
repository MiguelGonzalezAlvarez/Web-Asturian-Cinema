export interface User {
  id: number;
  username: string;
  email: string;
  fullName: string;
  avatarUrl: string;
  bio: string;
  roles: string[];
  isActive: boolean;
  followersCount: number;
  followingCount: number;
  moviesCount: number;
  createdAt: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  fullName: string;
}

export interface LoginRequest {
  usernameOrEmail: string;
  password: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
  user: User;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}
