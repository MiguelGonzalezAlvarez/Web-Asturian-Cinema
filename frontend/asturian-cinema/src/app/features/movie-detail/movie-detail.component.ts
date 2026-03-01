import { Component, OnInit, signal, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MovieService } from '../../core/services/movie.service';
import { AuthService } from '../../core/services/auth.service';
import { Movie } from '../../core/models/movie.model';
import { Review, CreateReviewRequest } from '../../core/models/review.model';

@Component({
  selector: 'app-movie-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  template: `
    <div class="movie-detail" *ngIf="movie()">
      <div class="backdrop" [style.backgroundImage]="'url(' + movie()!.backdropImage + ')'">
        <div class="backdrop-overlay"></div>
      </div>

      <div class="container content">
        <div class="movie-header">
          <div class="poster">
            @if (movie()!.coverImage) {
              <img [src]="movie()!.coverImage" [alt]="movie()!.title">
            } @else {
              <div class="poster-placeholder">{{ movie()!.title }}</div>
            }
          </div>

          <div class="movie-info">
            <h1>{{ movie()!.title }}</h1>
            @if (movie()!.originalTitle) {
              <p class="original-title">{{ movie()!.originalTitle }}</p>
            }
            
            <div class="meta">
              <span>{{ movie()!.year }}</span>
              <span>{{ movie()!.duration }} min</span>
              <span>{{ movie()!.genre }}</span>
              @if (movie()!.isAsturian) {
                <span class="asturian-badge">Asturiana</span>
              }
            </div>

            <div class="rating">
              <span class="stars">
                @for (star of [1,2,3,4,5]; track star) {
                  <span [class.filled]="star <= movie()!.averageRating">★</span>
                }
              </span>
              <span>{{ movie()!.averageRating | number:'1.1-1' }}</span>
              <span class="reviews">({{ movie()!.reviewCount }} reseñas)</span>
            </div>

            <p class="synopsis">{{ movie()!.synopsis }}</p>

            <div class="actions">
              @if (authService.isLoggedIn()) {
                <button 
                  (click)="toggleFavorite()" 
                  class="btn-secondary"
                  [class.active]="movie()!.isFavorite"
                >
                  {{ movie()!.isFavorite ? '❤️ En favoritos' : '🤍 Añadir a favoritos' }}
                </button>
              }
            </div>

            @if (movie()!.trailerUrl) {
              <a [href]="movie()!.trailerUrl" target="_blank" class="btn-primary">
                ▶ Ver Trailer
              </a>
            }
          </div>
        </div>

        <section class="details">
          <div class="detail-row" *ngIf="movie()!.director">
            <strong>Director:</strong> {{ movie()!.director }}
          </div>
          <div class="detail-row" *ngIf="movie()!.cast">
            <strong>Reparto:</strong> {{ movie()!.cast }}
          </div>
          <div class="detail-row" *ngIf="movie()!.productionCompany">
            <strong>Productora:</strong> {{ movie()!.productionCompany }}
          </div>
          <div class="detail-row" *ngIf="movie()!.filmingLocations">
            <strong>Ubicaciones:</strong> {{ movie()!.filmingLocations }}
          </div>
          <div class="detail-row">
            <strong>Idioma:</strong> {{ movie()!.language }}
          </div>
          <div class="detail-row" *ngIf="movie()!.origin">
            <strong>Origen:</strong> {{ movie()!.origin }}
          </div>
        </section>

        <section class="reviews-section">
          <h2>Reseñas</h2>
          
          @if (authService.isLoggedIn()) {
            <form (ngSubmit)="submitReview()" class="review-form">
              <div class="rating-input">
                <label>Tu valoración:</label>
                <div class="stars">
                  @for (star of [1,2,3,4,5]; track star) {
                    <button 
                      type="button"
                      (click)="newReview.rating = star"
                      [class.filled]="star <= newReview.rating"
                    >★</button>
                  }
                </div>
              </div>
              
              <textarea 
                [(ngModel)]="newReview.comment"
                name="comment"
                placeholder="Escribe tu reseña..."
                class="input"
                rows="4"
              ></textarea>
              
              <button type="submit" class="btn-primary">Publicar Reseña</button>
            </form>
          } @else {
            <p class="login-prompt">
              <a routerLink="/login">Inicia sesión</a> para escribir una reseña
            </p>
          }

          <div class="reviews-list">
            @for (review of reviews(); track review.id) {
              <div class="review-card">
                <div class="review-header">
                  <span class="review-author">{{ review.user?.username }}</span>
                  <span class="review-rating">
                    @for (star of [1,2,3,4,5]; track star) {
                      <span [class.filled]="star <= review.rating">★</span>
                    }
                  </span>
                </div>
                <p class="review-comment">{{ review.comment }}</p>
                <span class="review-date">{{ review.createdAt | date:'mediumDate' }}</span>
              </div>
            }
          </div>
        </section>
      </div>
    </div>
  `,
  styles: [`
    .movie-detail {
      position: relative;
      min-height: 100vh;
    }

    .backdrop {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      height: 60vh;
      background-size: cover;
      background-position: center;
      z-index: -1;
    }

    .backdrop-overlay {
      position: absolute;
      inset: 0;
      background: linear-gradient(to bottom, 
        rgba(10, 10, 15, 0.3) 0%, 
        rgba(10, 10, 15, 0.8) 70%, 
        rgba(10, 10, 15, 1) 100%
      );
    }

    .content {
      padding-top: 40vh;
      padding-bottom: 4rem;
    }

    .movie-header {
      display: grid;
      grid-template-columns: 300px 1fr;
      gap: 2rem;
      margin-bottom: 3rem;
    }

    .poster img {
      width: 100%;
      border-radius: 8px;
      box-shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
    }

    .poster-placeholder {
      width: 100%;
      aspect-ratio: 2/3;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #1a1a28;
      border-radius: 8px;
      color: #6b6b7b;
    }

    .movie-info h1 {
      font-size: 3rem;
      color: #f5f5f5;
      margin-bottom: 0.5rem;
    }

    .original-title {
      color: #6b6b7b;
      font-size: 1.25rem;
      margin-bottom: 1rem;
    }

    .meta {
      display: flex;
      gap: 1rem;
      flex-wrap: wrap;
      margin-bottom: 1rem;
      color: #a0a0b0;
    }

    .asturian-badge {
      background: #d4af37;
      color: #000;
      padding: 0.25rem 0.75rem;
      border-radius: 4px;
      font-size: 0.75rem;
      font-weight: 600;
    }

    .rating {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      margin-bottom: 1.5rem;
      font-size: 1.25rem;
    }

    .stars .filled {
      color: #d4af37;
    }

    .stars span:not(.filled) {
      color: #4a4a5a;
    }

    .reviews {
      font-size: 0.875rem;
      color: #6b6b7b;
    }

    .synopsis {
      color: #a0a0b0;
      line-height: 1.6;
      margin-bottom: 1.5rem;
    }

    .actions {
      display: flex;
      gap: 1rem;
      margin-bottom: 1.5rem;
    }

    .btn-secondary.active {
      background: #d4af37;
      color: #000;
    }

    .details {
      background: #1a1a28;
      border-radius: 8px;
      padding: 1.5rem;
      margin-bottom: 3rem;
    }

    .detail-row {
      padding: 0.5rem 0;
      border-bottom: 1px solid rgba(255, 255, 255, 0.05);
      color: #a0a0b0;
    }

    .detail-row:last-child {
      border-bottom: none;
    }

    .detail-row strong {
      color: #f5f5f5;
      margin-right: 0.5rem;
    }

    .reviews-section h2 {
      font-size: 2rem;
      color: #f5f5f5;
      margin-bottom: 2rem;
    }

    .review-form {
      background: #1a1a28;
      padding: 1.5rem;
      border-radius: 8px;
      margin-bottom: 2rem;
    }

    .rating-input {
      margin-bottom: 1rem;
    }

    .rating-input label {
      display: block;
      color: #a0a0b0;
      margin-bottom: 0.5rem;
    }

    .rating-input .stars {
      display: flex;
      gap: 0.25rem;
    }

    .rating-input .stars button {
      background: none;
      border: none;
      font-size: 1.5rem;
      color: #4a4a5a;
      cursor: pointer;
    }

    .rating-input .stars button.filled {
      color: #d4af37;
    }

    .review-form textarea {
      width: 100%;
      margin-bottom: 1rem;
    }

    .login-prompt {
      text-align: center;
      padding: 2rem;
      background: #1a1a28;
      border-radius: 8px;
      margin-bottom: 2rem;
      color: #6b6b7b;
    }

    .login-prompt a {
      color: #d4af37;
    }

    .reviews-list {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }

    .review-card {
      background: #1a1a28;
      padding: 1.5rem;
      border-radius: 8px;
    }

    .review-header {
      display: flex;
      justify-content: space-between;
      margin-bottom: 0.5rem;
    }

    .review-author {
      color: #d4af37;
      font-weight: 500;
    }

    .review-comment {
      color: #a0a0b0;
      line-height: 1.6;
      margin-bottom: 0.5rem;
    }

    .review-date {
      color: #6b6b7b;
      font-size: 0.875rem;
    }

    @media (max-width: 768px) {
      .movie-header {
        grid-template-columns: 1fr;
      }

      .poster {
        max-width: 250px;
        margin: 0 auto;
      }
    }
  `]
})
export class MovieDetailComponent implements OnInit {
  @Input() id!: number;

  movie = signal<Movie | null>(null);
  reviews = signal<Review[]>([]);
  newReview: CreateReviewRequest = { rating: 0, comment: '' };

  constructor(
    private movieService: MovieService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadMovie();
    this.loadReviews();
  }

  loadMovie(): void {
    this.movieService.getMovie(this.id).subscribe({
      next: (movie) => this.movie.set(movie),
      error: (err) => console.error('Error loading movie:', err)
    });
  }

  loadReviews(): void {
    this.movieService.getMovieReviews(this.id, 0, 10).subscribe({
      next: (response) => this.reviews.set(response.content),
      error: (err) => console.error('Error loading reviews:', err)
    });
  }

  toggleFavorite(): void {
    const movie = this.movie();
    if (!movie) return;

    if (movie.isFavorite) {
      this.movieService.removeFromFavorites(movie.id).subscribe({
        next: () => {
          movie.isFavorite = false;
          this.movie.set({ ...movie });
        }
      });
    } else {
      this.movieService.addToFavorites(movie.id).subscribe({
        next: () => {
          movie.isFavorite = true;
          this.movie.set({ ...movie });
        }
      });
    }
  }

  submitReview(): void {
    if (this.newReview.rating === 0) return;

    this.movieService.createReview(this.id, this.newReview).subscribe({
      next: (review) => {
        this.reviews.set([review, ...this.reviews()]);
        this.newReview = { rating: 0, comment: '' };
        this.loadMovie();
      }
    });
  }
}
