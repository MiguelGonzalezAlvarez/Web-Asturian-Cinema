import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap, catchError, of } from 'rxjs';
import { User, AuthResponse, LoginRequest, RegisterRequest, RefreshTokenRequest } from '../models/user.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  
  private currentUserSignal = signal<User | null>(null);
  private isAuthenticatedSignal = signal<boolean>(false);
  private tokenSignal = signal<string | null>(null);

  currentUser = this.currentUserSignal.asReadonly();
  isAuthenticated = this.isAuthenticatedSignal.asReadonly();
  token = this.tokenSignal.asReadonly();

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.loadStoredAuth();
  }

  private loadStoredAuth(): void {
    const token = localStorage.getItem('accessToken');
    const userStr = localStorage.getItem('user');
    
    if (token && userStr) {
      try {
        const user = JSON.parse(userStr);
        this.tokenSignal.set(token);
        this.currentUserSignal.set(user);
        this.isAuthenticatedSignal.set(true);
      } catch {
        this.clearAuth();
      }
    }
  }

  register(request: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, request).pipe(
      tap(response => this.handleAuthResponse(response)),
      catchError(error => {
        console.error('Registration error:', error);
        throw error;
      })
    );
  }

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request).pipe(
      tap(response => this.handleAuthResponse(response)),
      catchError(error => {
        console.error('Login error:', error);
        throw error;
      })
    );
  }

  refreshToken(): Observable<AuthResponse | null> {
    const refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) {
      return of(null);
    }

    return this.http.post<AuthResponse>(`${this.apiUrl}/refresh`, { refreshToken }).pipe(
      tap(response => this.handleAuthResponse(response)),
      catchError(error => {
        console.error('Token refresh error:', error);
        this.clearAuth();
        return of(null);
      })
    );
  }

  logout(): void {
    this.clearAuth();
    this.router.navigate(['/']);
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/me`).pipe(
      tap(user => {
        this.currentUserSignal.set(user);
        localStorage.setItem('user', JSON.stringify(user));
      })
    );
  }

  isLoggedIn(): boolean {
    return this.isAuthenticatedSignal();
  }

  hasRole(role: string): boolean {
    const user = this.currentUserSignal();
    return user?.roles?.includes(role) || false;
  }

  getToken(): string | null {
    return this.tokenSignal();
  }

  private handleAuthResponse(response: AuthResponse): void {
    localStorage.setItem('accessToken', response.accessToken);
    localStorage.setItem('refreshToken', response.refreshToken);
    localStorage.setItem('user', JSON.stringify(response.user));
    
    this.tokenSignal.set(response.accessToken);
    this.currentUserSignal.set(response.user);
    this.isAuthenticatedSignal.set(true);
  }

  private clearAuth(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('user');
    
    this.tokenSignal.set(null);
    this.currentUserSignal.set(null);
    this.isAuthenticatedSignal.set(false);
  }
}
