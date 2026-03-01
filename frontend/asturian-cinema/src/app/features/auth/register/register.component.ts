import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="auth-page">
      <div class="auth-container">
        <div class="auth-card">
          <h1>Crear Cuenta</h1>
          <p class="auth-subtitle">Únete a la comunidad</p>
          
          <form (ngSubmit)="onSubmit()" class="auth-form">
            <div class="form-group">
              <label for="username">Usuario</label>
              <input 
                type="text" 
                id="username"
                [(ngModel)]="user.username"
                name="username"
                class="input"
                placeholder="Elige un nombre de usuario"
                required
              >
            </div>
            
            <div class="form-group">
              <label for="email">Email</label>
              <input 
                type="email" 
                id="email"
                [(ngModel)]="user.email"
                name="email"
                class="input"
                placeholder="Tu correo electrónico"
                required
              >
            </div>

            <div class="form-group">
              <label for="fullName">Nombre Completo (opcional)</label>
              <input 
                type="text" 
                id="fullName"
                [(ngModel)]="user.fullName"
                name="fullName"
                class="input"
                placeholder="Tu nombre"
              >
            </div>
            
            <div class="form-group">
              <label for="password">Contraseña</label>
              <input 
                type="password" 
                id="password"
                [(ngModel)]="user.password"
                name="password"
                class="input"
                placeholder="Crea una contraseña"
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
                Crear Cuenta
              }
            </button>
          </form>

          <p class="auth-footer">
            ¿Ya tienes cuenta? <a routerLink="/login">Inicia Sesión</a>
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
export class RegisterComponent {
  user = {
    username: '',
    email: '',
    fullName: '',
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

    this.authService.register(this.user).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.loading.set(false);
        this.error.set(err.error?.message || 'Error al crear la cuenta');
      }
    });
  }
}
