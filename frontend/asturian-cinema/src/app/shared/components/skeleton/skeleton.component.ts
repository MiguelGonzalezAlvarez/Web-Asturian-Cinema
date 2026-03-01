import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-skeleton',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="skeleton" [ngClass]="variant">
      @if (variant === 'card') {
        <div class="skeleton-card">
          <div class="skeleton-image"></div>
          <div class="skeleton-content">
            <div class="skeleton-title"></div>
            <div class="skeleton-text"></div>
          </div>
        </div>
      }
      @if (variant === 'movie-detail') {
        <div class="skeleton-movie-detail">
          <div class="skeleton-backdrop"></div>
          <div class="skeleton-info">
            <div class="skeleton-title-lg"></div>
            <div class="skeleton-meta"></div>
            <div class="skeleton-text"></div>
            <div class="skeleton-text short"></div>
          </div>
        </div>
      }
      @if (variant === 'list') {
        <div class="skeleton-list">
          @for (item of items; track $index) {
            <div class="skeleton-list-item">
              <div class="skeleton-thumb"></div>
              <div class="skeleton-list-content">
                <div class="skeleton-title"></div>
                <div class="skeleton-text"></div>
              </div>
            </div>
          }
        </div>
      }
      @if (variant === 'profile') {
        <div class="skeleton-profile">
          <div class="skeleton-avatar"></div>
          <div class="skeleton-profile-info">
            <div class="skeleton-title-lg"></div>
            <div class="skeleton-text"></div>
          </div>
        </div>
      }
    </div>
  `,
  styles: [`
    @keyframes shimmer {
      0% { background-position: -200% 0; }
      100% { background-position: 200% 0; }
    }

    .skeleton > div {
      background: linear-gradient(90deg, #1a1a28 25%, #2a2a38 50%, #1a1a28 75%);
      background-size: 200% 100%;
      animation: shimmer 1.5s infinite;
      border-radius: 4px;
    }

    .skeleton-card {
      background: #1a1a28;
      border-radius: 8px;
      overflow: hidden;
    }

    .skeleton-image {
      height: 200px;
      width: 100%;
    }

    .skeleton-content {
      padding: 1rem;
    }

    .skeleton-title {
      height: 20px;
      width: 80%;
      margin-bottom: 0.5rem;
    }

    .skeleton-text {
      height: 14px;
      width: 60%;
    }

    .skeleton-text.short {
      width: 40%;
    }

    .skeleton-movie-detail .skeleton-backdrop {
      height: 400px;
      width: 100%;
      border-radius: 0;
    }

    .skeleton-info {
      padding: 2rem;
    }

    .skeleton-title-lg {
      height: 32px;
      width: 60%;
      margin-bottom: 1rem;
    }

    .skeleton-meta {
      height: 20px;
      width: 40%;
      margin-bottom: 1rem;
    }

    .skeleton-list-item {
      display: flex;
      gap: 1rem;
      padding: 1rem;
      border-bottom: 1px solid rgba(255, 255, 255, 0.05);
    }

    .skeleton-thumb {
      width: 80px;
      height: 120px;
      flex-shrink: 0;
      border-radius: 4px;
    }

    .skeleton-list-content {
      flex: 1;
    }

    .skeleton-profile {
      display: flex;
      gap: 2rem;
      align-items: center;
      padding: 2rem;
    }

    .skeleton-avatar {
      width: 150px;
      height: 150px;
      border-radius: 50%;
      flex-shrink: 0;
    }

    .skeleton-profile-info {
      flex: 1;
    }
  `]
})
export class SkeletonComponent {
  @Input() variant: 'card' | 'movie-detail' | 'list' | 'profile' = 'card';
  @Input() items: number[] = [1, 2, 3, 4, 5, 6];
}
