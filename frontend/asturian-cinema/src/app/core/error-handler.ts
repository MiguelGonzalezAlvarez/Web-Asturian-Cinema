import { Injectable, ErrorHandler, NgZone } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GlobalErrorHandler implements ErrorHandler {
  constructor(private ngZone: NgZone) {}

  handleError(error: Error | HttpErrorResponse): void {
    let errorMessage = 'Ha ocurrido un error inesperado';

    if (error instanceof HttpErrorResponse) {
      if (error.status === 0) {
        errorMessage = 'No se puede conectar con el servidor. Verifica tu conexión a internet.';
      } else if (error.status === 401) {
        errorMessage = 'Tu sesión ha expirado. Por favor, inicia sesión novamente.';
        this.redirectToLogin();
      } else if (error.status === 403) {
        errorMessage = 'No tienes permiso para realizar esta acción.';
      } else if (error.status === 404) {
        errorMessage = 'El recurso solicitado no existe.';
      } else if (error.status >= 500) {
        errorMessage = 'Error del servidor. Por favor, inténtalo más tarde.';
      } else if (error.error?.message) {
        errorMessage = error.error.message;
      }
    } else if (error instanceof Error) {
      if (error.message.includes('JWT')) {
        errorMessage = 'Tu sesión ha expirado. Por favor, inicia sesión novamente.';
        this.redirectToLogin();
      } else {
        errorMessage = error.message;
      }
    }

    console.error('Error global:', error);
    
    this.ngZone.run(() => {
      (window as any).showError?.(errorMessage);
    });
  }

  private redirectToLogin(): void {
    if (!window.location.pathname.includes('/login')) {
      localStorage.clear();
      window.location.href = '/login';
    }
  }
}
