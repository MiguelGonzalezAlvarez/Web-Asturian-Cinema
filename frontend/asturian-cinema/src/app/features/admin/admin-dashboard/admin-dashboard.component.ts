import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="admin-page">
      <div class="container">
        <h1>Panel de Administración</h1>
        
        <div class="dashboard-grid">
          <a routerLink="/admin/movies" class="dashboard-card">
            <span class="icon">🎬</span>
            <h3>Gestionar Películas</h3>
            <p>Añadir, editar o eliminar películas</p>
          </a>
          
          <a routerLink="/admin/users" class="dashboard-card">
            <span class="icon">👥</span>
            <h3>Gestionar Usuarios</h3>
            <p>Administrar usuarios y roles</p>
          </a>
          
          <a routerLink="/admin/posts" class="dashboard-card">
            <span class="icon">📝</span>
            <h3>Gestionar Blog</h3>
            <p>Crear y editar artículos</p>
          </a>
          
          <a routerLink="/admin/stats" class="dashboard-card">
            <span class="icon">📊</span>
            <h3>Estadísticas</h3>
            <p>Ver estadísticas del sitio</p>
          </a>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .admin-page { padding: 2rem 0; min-height: 80vh; }
    h1 { font-size: 2rem; color: #f5f5f5; margin-bottom: 2rem; }
    .dashboard-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 1.5rem; }
    .dashboard-card { display: block; background: #1a1a28; border: 1px solid rgba(255,255,255,0.08); border-radius: 8px; padding: 2rem; text-decoration: none; transition: all 0.3s; }
    .dashboard-card:hover { border-color: #d4af37; transform: translateY(-4px); }
    .icon { font-size: 2.5rem; display: block; margin-bottom: 1rem; }
    .dashboard-card h3 { color: #f5f5f5; font-size: 1.25rem; margin-bottom: 0.5rem; }
    .dashboard-card p { color: #6b6b7b; font-size: 0.875rem; }
  `]
})
export class AdminDashboardComponent {}
