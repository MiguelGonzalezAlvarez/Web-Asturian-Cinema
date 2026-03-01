import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="not-found-page">
      <div class="content">
        <div class="error-code">404</div>
        <h1>Página no encontrada</h1>
        <p>Lo sentimos, la página que buscas no existe o ha sido movida.</p>
        
        <div class="actions">
          <a routerLink="/" class="btn-primary">
            <span class="icon">🏠</span>
            Volver al inicio
          </a>
          <a routerLink="/catalog" class="btn-secondary">
            <span>🎬</span>
            Ver catálogo
          </a>
        </div>

        <div class="suggestions">
          <h3>También puedes:</h3>
          <ul>
            <li><a routerLink="/blog">Leer nuestro blog</a></li>
            <li><a routerLink="/movies/asturianas">Ver películas asturianas</a></li>
            <li><a routerLink="/register">Crear una cuenta</a></li>
          </ul>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .not-found-page {
      min-height: 80vh;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 2rem;
      background: linear-gradient(135deg, #0a0a0f 0%, #1a1a28 100%);
    }

    .content {
      text-align: center;
      max-width: 600px;
    }

    .error-code {
      font-size: 8rem;
      font-weight: 700;
      color: #d4af37;
      line-height: 1;
      margin-bottom: 1rem;
      text-shadow: 0 0 40px rgba(212, 175, 55, 0.3);
      animation: pulse 2s ease-in-out infinite;
    }

    @keyframes pulse {
      0%, 100% { opacity: 1; }
      50% { opacity: 0.7; }
    }

    h1 {
      font-size: 2rem;
      color: #f5f5f5;
      margin-bottom: 1rem;
    }

    p {
      color: #a0a0b0;
      font-size: 1.1rem;
      margin-bottom: 2rem;
      line-height: 1.6;
    }

    .actions {
      display: flex;
      gap: 1rem;
      justify-content: center;
      flex-wrap: wrap;
      margin-bottom: 3rem;
    }

    .btn-primary, .btn-secondary {
      display: inline-flex;
      align-items: center;
      gap: 0.5rem;
      padding: 0.75rem 1.5rem;
      border-radius: 8px;
      text-decoration: none;
      font-weight: 500;
      transition: all 0.3s;
    }

    .btn-primary {
      background: #d4af37;
      color: #000;
    }

    .btn-primary:hover {
      background: #f5d76e;
      transform: translateY(-2px);
    }

    .btn-secondary {
      background: rgba(255, 255, 255, 0.1);
      color: #f5f5f5;
      border: 1px solid rgba(255, 255, 255, 0.1);
    }

    .btn-secondary:hover {
      background: rgba(255, 255, 255, 0.15);
      border-color: #d4af37;
    }

    .suggestions {
      background: rgba(255, 255, 255, 0.05);
      border-radius: 12px;
      padding: 1.5rem;
    }

    .suggestions h3 {
      color: #d4af37;
      font-size: 1rem;
      margin-bottom: 1rem;
    }

    .suggestions ul {
      list-style: none;
      padding: 0;
      margin: 0;
    }

    .suggestions li {
      margin-bottom: 0.5rem;
    }

    .suggestions a {
      color: #a0a0b0;
      text-decoration: none;
      transition: color 0.3s;
    }

    .suggestions a:hover {
      color: #d4af37;
    }
  `]
})
export class NotFoundComponent {}
