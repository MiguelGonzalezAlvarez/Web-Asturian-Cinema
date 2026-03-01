import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-blog-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="blog-page">
      <div class="container">
        <h1>Blog de AsturianCinema</h1>
        <p class="subtitle">Noticias, entrevistas y actualidad del cine asturiano</p>
        
        <div class="posts-grid">
          <p class="coming-soon">Blog en construcción...</p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .blog-page { padding: 2rem 0; min-height: 80vh; }
    h1 { font-size: 2.5rem; color: #f5f5f5; margin-bottom: 0.5rem; }
    .subtitle { color: #6b6b7b; margin-bottom: 3rem; }
    .posts-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 2rem; }
    .coming-soon { color: #6b6b7b; text-align: center; padding: 3rem; font-size: 1.25rem; }
  `]
})
export class BlogListComponent {}
