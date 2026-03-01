import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';

export interface WebSocketMessage {
  type: string;
  payload: any;
}

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private client: Client | null = null;
  private connectedSubject = new BehaviorSubject<boolean>(false);
  private messagesSubject = new Subject<WebSocketMessage>();
  
  connected$ = this.connectedSubject.asObservable();
  messages$ = this.messagesSubject.asObservable();

  constructor(private authService: AuthService) {}

  connect(): void {
    if (this.client?.connected) return;

    this.client = new Client({
      brokerURL: `ws://${environment.apiUrl.replace('http://', '')}/ws`,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      connectHeaders: {
        Authorization: `Bearer ${this.authService.getToken()}`
      }
    });

    this.client.onConnect = () => {
      this.connectedSubject.next(true);
      this.subscribeToUserQueue();
      this.subscribeToMovieUpdates();
    };

    this.client.onDisconnect = () => {
      this.connectedSubject.next(false);
    };

    this.client.onStompError = (frame) => {
      console.error('STOMP error:', frame);
    };

    this.client.activate();
  }

  disconnect(): void {
    if (this.client) {
      this.client.deactivate();
    }
  }

  private subscribeToUserQueue(): void {
    const userId = this.authService.currentUser()?.id;
    if (!userId) return;

    this.client?.subscribe(`/user/queue/notifications`, (message) => {
      const notification = JSON.parse(message.body);
      this.messagesSubject.next({
        type: 'NOTIFICATION',
        payload: notification
      });
    });
  }

  private subscribeToMovieUpdates(): void {
    this.client?.subscribe('/topic/movie-updates', (message) => {
      const update = JSON.parse(message.body);
      this.messagesSubject.next({
        type: 'MOVIE_UPDATE',
        payload: update
      });
    });
  }

  sendMessage(destination: string, body: any): void {
    if (this.client?.connected) {
      this.client.publish({
        destination,
        body: JSON.stringify(body)
      });
    }
  }

  subscribeToMovie(movieId: number): void {
    this.client?.subscribe(`/topic/movie/${movieId}`, (message) => {
      const comment = JSON.parse(message.body);
      this.messagesSubject.next({
        type: 'NEW_COMMENT',
        payload: { movieId, comment }
      });
    });
  }

  unsubscribeFromMovie(movieId: number): void {
    // STOMP will handle unsubscription automatically
  }
}
