import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="auth-page">
      <div class="auth-container">
        <div class="auth-card">
          <h1>Iniciar Sesión</h1>
          <p class="auth-subtitle">Bienvenido de vuelta</p>
          
          <form (ngSubmit)="onSubmit()" class="auth-form">
            <div class="form-group">
              <label for="usernameOrEmail">Usuario o Email</label>
              <input 
                type="text" 
                id="usernameOrEmail"
                [(ngModel)]="credentials.usernameOrEmail"
                name="usernameOrEmail"
                class="input"
                placeholder="Introduce tu usuario o email"
                required
              >
            </div>
            
            <div class="form-group">
              <label for="password">Contraseña</label>
              <input 
                type="password" 
                id="password"
                [(ngModel)]="credentials.password"
                name="password"
                class="input"
                placeholder="Introduce tu contraseña"
                required
              >
            </div>

            @if (error()) {
              <div class="error-message">{{ error() }}</div>
            }

            <button type="submit" class="btn-primary" [disabled]="loading()">
              @if (loading()) {
                <span class="spinner"></span>
              } @else {
                Iniciar Sesión
              }
            </button>
          </form>

          <p class="auth-footer">
            ¿No tienes cuenta? <a routerLink="/register">Regístrate</a>
          </p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .auth-page {
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      background: linear-gradient(135deg, #0a0a0f 0%, #1a1a28 100%);
      padding: 2rem;
    }

    .auth-container {
      width: 100%;
      max-width: 400px;
    }

    .auth-card {
      background: #1a1a28;
      border: 1px solid rgba(255, 255, 255, 0.08);
      border-radius: 12px;
      padding: 2rem;
      animation: fadeInUp 0.5s ease-out;
    }

    h1 {
      font-size: 2rem;
      color: #f5f5f5;
      margin-bottom: 0.5rem;
      text-align: center;
    }

    .auth-subtitle {
      text-align: center;
      color: #6b6b7b;
      margin-bottom: 2rem;
    }

    .auth-form {
      display: flex;
      flex-direction: column;
      gap: 1.5rem;
    }

    .form-group {
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
    }

    label {
      color: #a0a0b0;
      font-size: 0.875rem;
    }

    .error-message {
      background: rgba(248, 113, 113, 0.1);
      border: 1px solid #f87171;
      color: #f87171;
      padding: 0.75rem;
      border-radius: 4px;
      font-size: 0.875rem;
    }

    button[type="submit"] {
      width: 100%;
      padding: 1rem;
      font-size: 1rem;
    }

    .auth-footer {
      text-align: center;
      margin-top: 1.5rem;
      color: #6b6b7b;
    }

    .auth-footer a {
      color: #d4af37;
      font-weight: 500;
    }

    .spinner {
      display: inline-block;
      width: 20px;
      height: 20px;
      border: 2px solid transparent;
      border-top-color: currentColor;
      border-radius: 50%;
      animation: spin 0.8s linear infinite;
    }

    @keyframes spin {
      to { transform: rotate(360deg); }
    }

    @keyframes fadeInUp {
      from {
        opacity: 0;
        transform: translateY(20px);
      }
      to {
        opacity: 1;
        transform: translateY(0);
      }
    }
  `]
})
export class LoginComponent {
  credentials = {
    usernameOrEmail: '',
    password: ''
  };

  loading = signal(false);
  error = signal('');

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.loading.set(true);
    this.error.set('');

    this.authService.login(this.credentials).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.loading.set(false);
        this.error.set(err.error?.message || 'Credenciales incorrectas');
      }
    });
  }
}
