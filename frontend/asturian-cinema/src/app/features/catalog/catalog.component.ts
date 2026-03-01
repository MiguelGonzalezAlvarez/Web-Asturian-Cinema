import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MovieService } from '../../core/services/movie.service';
import { Movie } from '../../core/models/movie.model';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  template: `
    <div class="catalog-page">
      <div class="container">
        <header class="catalog-header">
          <h1>{{ isAsturian() ? 'Películas Asturianas' : 'Catálogo' }}</h1>
          
          <div class="search-bar">
            <input 
              type="text" 
              [(ngModel)]="searchQuery"
              (keyup.enter)="onSearch()"
              placeholder="Buscar películas..."
              class="input"
            >
            <button (click)="onSearch()" class="btn-primary">Buscar</button>
          </div>
        </header>

        <div class="filters">
          <select [(ngModel)]="selectedGenre" (change)="onGenreChange()" class="input">
            <option value="">Todos los géneros</option>
            @for (genre of genres; track genre) {
              <option [value]="genre">{{ genre }}</option>
            }
          </select>
          
          <select [(ngModel)]="selectedYear" (change)="onYearChange()" class="input">
            <option value="">Todos los años</option>
            @for (year of years; track year) {
              <option [value]="year">{{ year }}</option>
            }
          </select>
        </div>

        @if (loading()) {
          <div class="loading-grid">
            @for (i of [1,2,3,4,5,6]; track i) {
              <div class="skeleton-card">
                <div class="skeleton poster"></div>
                <div class="skeleton title"></div>
                <div class="skeleton subtitle"></div>
              </div>
            }
          </div>
        } @else if (movies().length === 0) {
          <div class="empty-state">
            <p>No se encontraron películas</p>
          </div>
        } @else {
          <div class="movies-grid">
            @for (movie of movies(); track movie.id) {
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
                  @if (movie.isAsturian) {
                    <div class="asturian-badge">AST</div>
                  }
                </div>
                <div class="movie-info">
                  <h3>{{ movie.title }}</h3>
                  <p>{{ movie.year }} • {{ movie.genre }}</p>
                </div>
              </a>
            }
          </div>
        }

        @if (totalPages() > 1) {
          <div class="pagination">
            <button 
              [disabled]="currentPage() === 0" 
              (click)="goToPage(currentPage() - 1)"
              class="btn-secondary"
            >
              Anterior
            </button>
            <span>Página {{ currentPage() + 1 }} de {{ totalPages() }}</span>
            <button 
              [disabled]="currentPage() >= totalPages() - 1" 
              (click)="goToPage(currentPage() + 1)"
              class="btn-secondary"
            >
              Siguiente
            </button>
          </div>
        }
      </div>
    </div>
  `,
  styles: [`
    .catalog-page {
      padding: 2rem 0;
      min-height: 80vh;
    }

    .catalog-header {
      margin-bottom: 2rem;
    }

    .catalog-header h1 {
      font-size: 2.5rem;
      color: #f5f5f5;
      margin-bottom: 1rem;
    }

    .search-bar {
      display: flex;
      gap: 1rem;
      max-width: 500px;
    }

    .search-bar input {
      flex: 1;
    }

    .filters {
      display: flex;
      gap: 1rem;
      margin-bottom: 2rem;
    }

    .filters select {
      min-width: 150px;
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

    .pagination {
      display: flex;
      justify-content: center;
      align-items: center;
      gap: 1rem;
      margin-top: 3rem;
    }

    .loading-grid, .empty-state {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
      gap: 2rem;
    }

    .skeleton-card {
      background: #1a1a28;
      border-radius: 8px;
      overflow: hidden;
    }

    .skeleton {
      background: linear-gradient(90deg, #222233 25%, #2a2a3d 50%, #222233 75%);
      background-size: 200% 100%;
      animation: shimmer 1.5s infinite;
    }

    .skeleton.poster {
      aspect-ratio: 2/3;
    }

    .skeleton.title {
      height: 20px;
      margin: 1rem 1rem 0.5rem;
    }

    .skeleton.subtitle {
      height: 14px;
      margin: 0 1rem 1rem;
    }

    .empty-state {
      text-align: center;
      padding: 4rem;
      color: #6b6b7b;
    }

    @keyframes shimmer {
      0% { background-position: 200% 0; }
      100% { background-position: -200% 0; }
    }
  `]
})
export class CatalogComponent implements OnInit {
  movies = signal<Movie[]>([]);
  loading = signal(true);
  currentPage = signal(0);
  totalPages = signal(0);
  totalElements = signal(0);
  isAsturian = signal(false);

  searchQuery = '';
  selectedGenre = '';
  selectedYear = '';

  genres = ['Drama', 'Comedia', 'Documental', 'Ciencia Ficción', 'Thriller', 'Corto'];
  years = [2024, 2023, 2022, 2021, 2020, 2019, 2018, 2017, 2016, 2015];

  constructor(
    private movieService: MovieService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.url.subscribe(url => {
      const isAsturian = url.some(segment => segment.path === 'asturianas');
      this.isAsturian.set(isAsturian);
      this.loadMovies();
    });

    this.route.queryParams.subscribe(params => {
      if (params['genre']) {
        this.selectedGenre = params['genre'];
        this.loadMovies();
      }
    });
  }

  loadMovies(): void {
    this.loading.set(true);

    const page = this.currentPage();
    const size = 12;

    let observable;
    if (this.searchQuery) {
      observable = this.movieService.searchMovies(this.searchQuery, page, size);
    } else if (this.isAsturian()) {
      observable = this.movieService.getAsturianMovies(page, size);
    } else if (this.selectedGenre) {
      observable = this.movieService.getMoviesByGenre(this.selectedGenre, page, size);
    } else {
      observable = this.movieService.getMovies(page, size);
    }

    observable.subscribe({
      next: (response) => {
        this.movies.set(response.content);
        this.totalPages.set(response.totalPages);
        this.totalElements.set(response.totalElements);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading movies:', err);
        this.loading.set(false);
      }
    });
  }

  onSearch(): void {
    this.currentPage.set(0);
    this.loadMovies();
  }

  onGenreChange(): void {
    this.currentPage.set(0);
    this.loadMovies();
  }

  onYearChange(): void {
    this.currentPage.set(0);
    this.loadMovies();
  }

  goToPage(page: number): void {
    this.currentPage.set(page);
    this.loadMovies();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
