import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { MovieService } from '../../core/services/movie.service';
import { Movie } from '../../core/models/movie.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="profile-page">
      <div class="container">
        <div class="profile-header">
          <div class="avatar">
            @if (user()?.avatarUrl) {
              <img [src]="user()!.avatarUrl" [alt]="user()!.username">
            } @else {
              <span>{{ user()?.username?.charAt(0) | uppercase }}</span>
            }
          </div>
          <div class="profile-info">
            <h1>{{ user()?.username }}</h1>
            <p>{{ user()?.fullName }}</p>
            <p class="bio">{{ user()?.bio }}</p>
            <div class="stats">
              <div class="stat">
                <strong>{{ user()?.followersCount }}</strong>
                <span>Seguidores</span>
              </div>
              <div class="stat">
                <strong>{{ user()?.followingCount }}</strong>
                <span>Siguiendo</span>
              </div>
              <div class="stat">
                <strong>{{ favorites().length }}</strong>
                <span>Favoritas</span>
              </div>
            </div>
          </div>
        </div>

        <section class="favorites">
          <h2>Mis Películas Favoritas</h2>
          @if (favorites().length === 0) {
            <p class="empty">No tienes películas favoritas aún</p>
          } @else {
            <div class="movies-grid">
              @for (movie of favorites(); track movie.id) {
                <a [routerLink]="['/movie', movie.id]" class="movie-card">
                  <div class="movie-poster">
                    @if (movie.coverImage) {
                      <img [src]="movie.coverImage" [alt]="movie.title">
                    } @else {
                      <div class="poster-placeholder">{{ movie.title }}</div>
                    }
                  </div>
                  <div class="movie-info">
                    <h3>{{ movie.title }}</h3>
                  </div>
                </a>
              }
            </div>
          }
        </section>
      </div>
    </div>
  `,
  styles: [`
    .profile-page { padding: 2rem 0; min-height: 80vh; }
    .profile-header { display: flex; gap: 2rem; margin-bottom: 3rem; align-items: center; }
    .avatar { width: 150px; height: 150px; border-radius: 50%; background: #1a1a28; display: flex; align-items: center; justify-content: center; overflow: hidden; }
    .avatar img { width: 100%; height: 100%; object-fit: cover; }
    .avatar span { font-size: 3rem; color: #d4af37; }
    .profile-info h1 { font-size: 2rem; color: #f5f5f5; margin-bottom: 0.5rem; }
    .profile-info p { color: #a0a0b0; margin-bottom: 0.5rem; }
    .stats { display: flex; gap: 2rem; margin-top: 1rem; }
    .stat { text-align: center; }
    .stat strong { display: block; font-size: 1.5rem; color: #d4af37; }
    .stat span { font-size: 0.875rem; color: #6b6b7b; }
    .movies-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(180px, 1fr)); gap: 1.5rem; }
    .movie-card { display: block; border-radius: 8px; overflow: hidden; background: #1a1a28; transition: transform 0.3s; text-decoration: none; }
    .movie-card:hover { transform: translateY(-4px); }
    .movie-poster { aspect-ratio: 2/3; }
    .movie-poster img { width: 100%; height: 100%; object-fit: cover; }
    .poster-placeholder { width: 100%; height: 100%; display: flex; align-items: center; justify-content: center; background: #222233; color: #6b6b7b; padding: 1rem; text-align: center; }
    .movie-info { padding: 1rem; }
    .movie-info h3 { color: #f5f5f5; font-size: 0.875rem; }
    .empty { color: #6b6b7b; text-align: center; padding: 2rem; }
    .favorites h2 { color: #f5f5f5; margin-bottom: 1.5rem; }
  `]
})
export class ProfileComponent implements OnInit {
  favorites = signal<Movie[]>([]);

  constructor(
    public authService: AuthService,
    private movieService: MovieService
  ) {}

  get user() {
    return this.authService.currentUser;
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.authService.getCurrentUser().subscribe();
      this.loadFavorites();
    }
  }

  loadFavorites(): void {
    this.movieService.getFavorites(0, 12).subscribe({
      next: (response) => this.favorites.set(response.content),
      error: (err) => console.error('Error loading favorites:', err)
    });
  }
}
