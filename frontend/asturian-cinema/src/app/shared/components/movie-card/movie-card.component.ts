import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Movie } from '../../../core/models/movie.model';

@Component({
  selector: 'app-movie-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <a [routerLink]="['/movie', movie.id]" class="movie-card" [class.asturian]="movie.isAsturian">
      <div class="poster">
        @if (movie.coverImage) {
          <img [src]="movie.coverImage" [alt]="movie.title" loading="lazy">
        } @else {
          <div class="poster-placeholder">
            <span>{{ movie.title }}</span>
          </div>
        }
        
        <div class="overlay">
          @if (showRating) {
            <div class="rating">
              <span class="star">★</span>
              <span>{{ movie.averageRating | number:'1.1-1' }}</span>
            </div>
          }
          
          @if (movie.isAsturian) {
            <div class="badge asturian">AST</div>
          }
          
          @if (showFavorite) {
            <button class="favorite-btn" (click)="onFavoriteClick($event)">
              {{ movie.isFavorite ? '❤️' : '🤍' }}
            </button>
          }
        </div>
      </div>
      
      <div class="info">
        <h3>{{ movie.title }}</h3>
        <p>{{ movie.year }} • {{ movie.genre }}</p>
      </div>
    </a>
  `,
  styles: [`
    .movie-card {
      display: block;
      border-radius: 8px;
      overflow: hidden;
      background: var(--color-bg-card, #1a1a28);
      transition: all 0.3s ease;
      text-decoration: none;
    }

    .movie-card:hover {
      transform: translateY(-8px);
      box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
    }

    .movie-card.asturian {
      border: 1px solid rgba(212, 175, 55, 0.3);
    }

    .poster {
      position: relative;
      aspect-ratio: 2/3;
      overflow: hidden;
    }

    .poster img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.3s ease;
    }

    .movie-card:hover .poster img {
      transform: scale(1.05);
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
      font-size: 0.875rem;
    }

    .overlay {
      position: absolute;
      inset: 0;
      background: linear-gradient(to top, rgba(0,0,0,0.8) 0%, transparent 50%);
      opacity: 0;
      transition: opacity 0.3s ease;
      display: flex;
      flex-direction: column;
      justify-content: flex-end;
      padding: 0.75rem;
    }

    .movie-card:hover .overlay {
      opacity: 1;
    }

    .rating {
      position: absolute;
      top: 0.5rem;
      right: 0.5rem;
      background: rgba(0, 0, 0, 0.7);
      color: #d4af37;
      padding: 0.25rem 0.5rem;
      border-radius: 4px;
      font-size: 0.75rem;
      display: flex;
      align-items: center;
      gap: 0.25rem;
    }

    .rating .star {
      font-size: 0.875rem;
    }

    .badge {
      position: absolute;
      bottom: 0.5rem;
      left: 0.5rem;
      background: #d4af37;
      color: #000;
      padding: 0.25rem 0.5rem;
      border-radius: 4px;
      font-size: 0.625rem;
      font-weight: 700;
      letter-spacing: 0.5px;
    }

    .favorite-btn {
      position: absolute;
      top: 0.5rem;
      left: 0.5rem;
      background: rgba(0, 0, 0, 0.7);
      border: none;
      border-radius: 50%;
      width: 32px;
      height: 32px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      font-size: 1rem;
      transition: transform 0.2s ease;
    }

    .favorite-btn:hover {
      transform: scale(1.1);
    }

    .info {
      padding: 0.75rem;
    }

    .info h3 {
      font-size: 0.875rem;
      color: var(--color-text-primary, #f5f5f5);
      margin-bottom: 0.25rem;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .info p {
      font-size: 0.75rem;
      color: var(--color-text-muted, #6b6b7b);
    }
  `]
})
export class MovieCardComponent {
  @Input() movie!: Movie;
  @Input() showRating = true;
  @Input() showFavorite = false;
  @Output() favorite = new EventEmitter<number>();

  onFavoriteClick(event: Event): void {
    event.preventDefault();
    event.stopPropagation();
    this.favorite.emit(this.movie.id);
  }
}
