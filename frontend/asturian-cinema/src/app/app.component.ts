import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink } from '@angular/router';
import { AuthService } from './core/services/auth.service';
import { ThemeService } from './core/services/theme.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  template: `
    <div class="app-container">
      <nav class="navbar">
        <div class="container nav-content">
          <a routerLink="/" class="logo">
            <span class="logo-icon">🎬</span>
            <span class="logo-text">AsturianCinema</span>
          </a>

          <div class="nav-links">
            <a routerLink="/catalog">Catálogo</a>
            <a routerLink="/movies/asturianas">Asturianas</a>
            <a routerLink="/blog">Blog</a>
          </div>

          <div class="nav-actions">
            <button class="theme-toggle" (click)="themeService.toggleTheme()" [attr.aria-label]="'Cambiar tema'">
              {{ themeService.theme() === 'dark' ? '☀️' : '🌙' }}
            </button>

            @if (authService.isAuthenticated()) {
              <a routerLink="/profile" class="nav-user">
                <span class="user-avatar">{{ authService.currentUser()?.username?.charAt(0) | uppercase }}</span>
              </a>
              @if (authService.hasRole('ADMIN')) {
                <a routerLink="/admin" class="btn-admin">Admin</a>
              }
              <button (click)="authService.logout()" class="btn-logout">Cerrar</button>
            } @else {
              <a routerLink="/login" class="btn-login">Iniciar Sesión</a>
            }
          </div>
        </div>
      </nav>

      <main class="main-content">
        <router-outlet></router-outlet>
      </main>

      <footer class="footer">
        <div class="container">
          <div class="footer-content">
            <div class="footer-brand">
              <span class="logo-icon">🎬</span>
              <span>AsturianCinema</span>
            </div>
            <p class="footer-description">
              Descubre el cine de Asturias. Una plataforma dedicada a promover 
              las películas realizadas en Asturias y basadas en la cultura regional.
            </p>
            <div class="footer-links">
              <a routerLink="/about">Acerca de</a>
              <a routerLink="/contact">Contacto</a>
              <a routerLink="/privacy">Privacidad</a>
            </div>
          </div>
          <div class="footer-bottom">
            <p>© 2024 AsturianCinema. Todos los derechos reservados.</p>
          </div>
        </div>
      </footer>
    </div>
  `,
  styles: [`
    .app-container {
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }

    .navbar {
      position: sticky;
      top: 0;
      z-index: 100;
      background: rgba(10, 10, 15, 0.95);
      backdrop-filter: blur(10px);
      border-bottom: 1px solid rgba(255, 255, 255, 0.08);
    }

    .nav-content {
      display: flex;
      align-items: center;
      justify-content: space-between;
      height: 70px;
    }

    .logo {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      text-decoration: none;
    }

    .logo-icon {
      font-size: 1.5rem;
    }

    .logo-text {
      font-family: 'Playfair Display', serif;
      font-size: 1.25rem;
      font-weight: 600;
      color: #d4af37;
    }

    .nav-links {
      display: flex;
      gap: 2rem;
    }

    .nav-links a {
      color: #a0a0b0;
      text-decoration: none;
      font-size: 0.9375rem;
      transition: color 0.3s;
    }

    .nav-links a:hover {
      color: #d4af37;
    }

    .nav-actions {
      display: flex;
      align-items: center;
      gap: 1rem;
    }

    .theme-toggle {
      background: none;
      border: none;
      font-size: 1.25rem;
      cursor: pointer;
      padding: 0.5rem;
      border-radius: 4px;
      transition: background 0.3s;
    }

    .theme-toggle:hover {
      background: rgba(255, 255, 255, 0.1);
    }

    .nav-user {
      display: flex;
      align-items: center;
    }

    .user-avatar {
      width: 36px;
      height: 36px;
      border-radius: 50%;
      background: #d4af37;
      color: #000;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 600;
      font-size: 0.875rem;
    }

    .btn-admin, .btn-logout {
      color: #6b6b7b;
      font-size: 0.875rem;
      cursor: pointer;
      background: none;
      border: none;
    }

    .btn-admin:hover, .btn-logout:hover {
      color: #d4af37;
    }

    .btn-login {
      background: #d4af37;
      color: #000;
      padding: 0.5rem 1rem;
      border-radius: 4px;
      text-decoration: none;
      font-weight: 500;
      font-size: 0.875rem;
      transition: all 0.3s;
    }

    .btn-login:hover {
      background: #f5d76e;
    }

    .main-content {
      flex: 1;
    }

    .footer {
      background: #0a0a0f;
      border-top: 1px solid rgba(255, 255, 255, 0.08);
      padding: 3rem 0 1rem;
      margin-top: auto;
    }

    .footer-content {
      margin-bottom: 2rem;
    }

    .footer-brand {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      font-family: 'Playfair Display', serif;
      font-size: 1.25rem;
      color: #d4af37;
      margin-bottom: 1rem;
    }

    .footer-description {
      color: #6b6b7b;
      line-height: 1.6;
      max-width: 500px;
      margin-bottom: 1.5rem;
    }

    .footer-links {
      display: flex;
      gap: 1.5rem;
    }

    .footer-links a {
      color: #a0a0b0;
      text-decoration: none;
      font-size: 0.875rem;
    }

    .footer-links a:hover {
      color: #d4af37;
    }

    .footer-bottom {
      padding-top: 1.5rem;
      border-top: 1px solid rgba(255, 255, 255, 0.08);
    }

    .footer-bottom p {
      color: #4a4a5a;
      font-size: 0.875rem;
    }

    @media (max-width: 768px) {
      .nav-links {
        display: none;
      }
    }
  `]
})
export class AppComponent {
  constructor(
    public authService: AuthService,
    public themeService: ThemeService
  ) {}
}
