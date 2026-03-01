import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface AdminStats {
  totalUsers: number;
  activeUsers: number;
  totalMovies: number;
  publishedMovies: number;
  asturianMovies: number;
  totalReviews: number;
  publishedPosts: number;
}

export interface UserManagement {
  id: number;
  username: string;
  email: string;
  fullName: string;
  avatarUrl: string;
  roles: string[];
  isActive: boolean;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = `${environment.apiUrl}/admin`;

  constructor(private http: HttpClient) {}

  getStats(): Observable<AdminStats> {
    return this.http.get<AdminStats>(`${this.apiUrl}/stats`);
  }

  getUsers(page: number = 0, size: number = 20, search?: string): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    if (search) {
      params = params.set('search', search);
    }

    return this.http.get<any>(`${this.apiUrl}/users`, { params });
  }

  updateUserRole(userId: number, role: string): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/users/${userId}/role`, { role });
  }

  toggleUserActive(userId: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/users/${userId}/active`, {});
  }

  deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/users/${userId}`);
  }

  getAllMovies(page: number = 0, size: number = 20, published?: boolean): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    if (published !== undefined) {
      params = params.set('published', published.toString());
    }

    return this.http.get<any>(`${this.apiUrl}/movies/all`, { params });
  }
}
