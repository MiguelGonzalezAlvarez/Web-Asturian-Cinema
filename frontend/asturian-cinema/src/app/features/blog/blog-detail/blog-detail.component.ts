import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-blog-detail',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="blog-detail">
      <div class="container">
        <p class="coming-soon">Artículo en construcción...</p>
      </div>
    </div>
  `,
  styles: [`
    .blog-detail { padding: 2rem 0; min-height: 80vh; }
    .coming-soon { color: #6b6b7b; text-align: center; padding: 3rem; font-size: 1.25rem; }
  `]
})
export class BlogDetailComponent {}
