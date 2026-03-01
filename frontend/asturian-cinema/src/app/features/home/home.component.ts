import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MovieService } from '../../core/services/movie.service';
import { Movie } from '../../core/models/movie.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="home">
      <!-- Hero Section -->
      <section class="hero">
        <div class="hero-content">
          <h1 class="hero-title">AsturianCinema</h1>
          <p class="hero-subtitle">Descubre el cine de Asturias</p>
          <div class="hero-buttons">
            <a routerLink="/catalog" class="btn-primary">Explorar Catálogo</a>
            <a routerLink="/movies/asturianas" class="btn-secondary">Películas Asturianas</a>
          </div>
        </div>
        <div class="hero-overlay"></div>
      </section>

      <!-- Featured Movies -->
      <section class="featured-section">
        <div class="container">
          <h2 class="section-title">Películas Destacadas</h2>
          <div class="movies-grid">
            @for (movie of featuredMovies(); track movie.id) {
              <a [routerLink]="['/movie', movie.id]" class="movie-card">
                <div class="movie-poster">
                  @if (movie.coverImage) {
                    <img [src]="movie.coverImage" [alt]="movie.title">
                  } @else {
                    <div class="poster-placeholder">
                      <span>{{ movie.title }}</span>
                    </div>
                  }
                  <div class="movie-rating">
                    <span>★</span> {{ movie.averageRating | number:'1.1-1' }}
                  </div>
                </div>
                <div class="movie-info">
                  <h3>{{ movie.title }}</h3>
                  <p>{{ movie.year }} • {{ movie.genre }}</p>
                </div>
              </a>
            }
          </div>
        </div>
      </section>

      <!-- Asturian Movies -->
      <section class="asturian-section">
        <div class="container">
          <h2 class="section-title">Cine Asturiano</h2>
          <p class="section-description">
            Explora películas realizadas en Asturias o basadas en la cultura regional
          </p>
          <div class="movies-grid">
            @for (movie of asturianMovies(); track movie.id) {
              <a [routerLink]="['/movie', movie.id]" class="movie-card">
                <div class="movie-poster">
                  @if (movie.coverImage) {
                    <img [src]="movie.coverImage" [alt]="movie.title">
                  } @else {
                    <div class="poster-placeholder">
                      <span>{{ movie.title }}</span>
                    </div>
                  }
                  <div class="asturian-badge">�ast</div>
                </div>
                <div class="movie-info">
                  <h3>{{ movie.title }}</h3>
                  <p>{{ movie.year }}</p>
                </div>
              </a>
            }
          </div>
          <div class="see-more">
            <a routerLink="/movies/asturianas" class="btn-secondary">Ver Todas las Asturianass</a>
          </div>
        </div>
      </section>

      <!-- Categories -->
      <section class="categories-section">
        <div class="container">
          <h2 class="section-title">Explora por Género</h2>
          <div class="categories-grid">
            @for (category of categories; track category.name) {
              <a [routerLink]="['/catalog']" [queryParams]="{genre: category.name}" class="category-card">
                <span class="category-icon">{{ category.icon }}</span>
                <span class="category-name">{{ category.name }}</span>
              </a>
            }
          </div>
        </div>
      </section>
    </div>
  `,
  styles: [`
    .hero {
      position: relative;
      height: 80vh;
      min-height: 600px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: linear-gradient(135deg, #0a0a0f 0%, #1a1a28 100%);
      overflow: hidden;
    }

    .hero::before {
      content: '';
      position: absolute;
      inset: 0;
      background: 
        radial-gradient(circle at 20% 80%, rgba(212, 175, 55, 0.1) 0%, transparent 50%),
        radial-gradient(circle at 80% 20%, rgba(212, 175, 55, 0.05) 0%, transparent 40%);
    }

    .hero-content {
      position: relative;
      z-index: 2;
      text-align: center;
      padding: 2rem;
      animation: fadeInUp 0.8s ease-out;
    }

    .hero-title {
      font-size: 4rem;
      font-weight: 700;
      color: #d4af37;
      margin-bottom: 1rem;
      text-shadow: 0 0 40px rgba(212, 175, 55, 0.3);
    }

    .hero-subtitle {
      font-size: 1.5rem;
      color: #a0a0b0;
      margin-bottom: 2rem;
    }

    .hero-buttons {
      display: flex;
      gap: 1rem;
      justify-content: center;
      flex-wrap: wrap;
    }

    .featured-section, .asturian-section, .categories-section {
      padding: 4rem 0;
    }

    .section-title {
      font-size: 2rem;
      color: #f5f5f5;
      margin-bottom: 2rem;
      text-align: center;
    }

    .section-description {
      text-align: center;
      color: #a0a0b0;
      margin-bottom: 2rem;
      max-width: 600px;
      margin-left: auto;
      margin-right: auto;
    }

    .movies-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
      gap: 2rem;
    }

    .movie-card {
      display: block;
      border-radius: 8px;
      overflow: hidden;
      background: #1a1a28;
      transition: all 0.3s ease;
      text-decoration: none;
    }

    .movie-card:hover {
      transform: translateY(-8px);
      box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
    }

    .movie-poster {
      position: relative;
      aspect-ratio: 2/3;
      overflow: hidden;
    }

    .movie-poster img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .poster-placeholder {
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #222233;
      color: #6b6b7b;
      padding: 1rem;
      text-align: center;
    }

    .movie-rating {
      position: absolute;
      top: 0.5rem;
      right: 0.5rem;
      background: rgba(0, 0, 0, 0.7);
      color: #d4af37;
      padding: 0.25rem 0.5rem;
      border-radius: 4px;
      font-size: 0.875rem;
    }

    .asturian-badge {
      position: absolute;
      bottom: 0.5rem;
      left: 0.5rem;
      background: #d4af37;
      color: #000;
      padding: 0.25rem 0.5rem;
      border-radius: 4px;
      font-size: 0.75rem;
      font-weight: 600;
    }

    .movie-info {
      padding: 1rem;
    }

    .movie-info h3 {
      font-size: 1rem;
      color: #f5f5f5;
      margin-bottom: 0.25rem;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .movie-info p {
      font-size: 0.875rem;
      color: #6b6b7b;
    }

    .see-more {
      text-align: center;
      margin-top: 2rem;
    }

    .categories-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
      gap: 1rem;
    }

    .category-card {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 2rem;
      background: #1a1a28;
      border: 1px solid rgba(255, 255, 255, 0.08);
      border-radius: 8px;
      text-decoration: none;
      transition: all 0.3s ease;
    }

    .category-card:hover {
      border-color: #d4af37;
      transform: translateY(-4px);
    }

    .category-icon {
      font-size: 2rem;
      margin-bottom: 0.5rem;
    }

    .category-name {
      color: #f5f5f5;
      font-size: 0.875rem;
    }

    @keyframes fadeInUp {
      from {
        opacity: 0;
        transform: translateY(30px);
      }
      to {
        opacity: 1;
        transform: translateY(0);
      }
    }
  `]
})
export class HomeComponent implements OnInit {
  featuredMovies = signal<Movie[]>([]);
  asturianMovies = signal<Movie[]>([]);

  categories = [
    { name: 'Drama', icon: '🎭' },
    { name: 'Comedia', icon: '😂' },
    { name: 'Documental', icon: '📹' },
    { name: 'Ciencia Ficción', icon: '🚀' },
    { name: 'Thriller', icon: '🔪' },
    { name: 'Corto', icon: '🎬' }
  ];

  constructor(private movieService: MovieService) {}

  ngOnInit(): void {
    this.loadFeaturedMovies();
    this.loadAsturianMovies();
  }

  loadFeaturedMovies(): void {
    this.movieService.getMovies(0, 6).subscribe({
      next: (response) => {
        this.featuredMovies.set(response.content);
      },
      error: (err) => console.error('Error loading featured movies:', err)
    });
  }

  loadAsturianMovies(): void {
    this.movieService.getAsturianMovies(0, 6).subscribe({
      next: (response) => {
        this.asturianMovies.set(response.content);
      },
      error: (err) => console.error('Error loading asturian movies:', err)
    });
  }
}
