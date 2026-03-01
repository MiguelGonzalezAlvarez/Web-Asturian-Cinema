import { Injectable, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Notification {
  id: number;
  type: string;
  message: string;
  link: string;
  isRead: boolean;
  fromUser: {
    id: number;
    username: string;
    avatarUrl: string;
  };
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private apiUrl = `${environment.apiUrl}/users`;
  
  notifications = signal<Notification[]>([]);
  unreadCount = signal<number>(0);

  constructor(private http: HttpClient) {}

  getNotifications(page: number = 0, size: number = 20): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<any>(`${this.apiUrl}/notifications`, { params });
  }

  getUnreadCount(): Observable<{count: number}> {
    return this.http.get<{count: number}>(`${this.apiUrl}/notifications/unread-count`);
  }

  markAllAsRead(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/notifications/read-all`, {});
  }

  loadNotifications(): void {
    this.getNotifications().subscribe({
      next: (response) => {
        this.notifications.set(response.content);
      }
    });
  }

  loadUnreadCount(): void {
    this.getUnreadCount().subscribe({
      next: (response) => {
        this.unreadCount.set(response.count);
      }
    });
  }

  addNotification(notification: Notification): void {
    this.notifications.update(current => [notification, ...current]);
    this.unreadCount.update(count => count + 1);
  }
}
