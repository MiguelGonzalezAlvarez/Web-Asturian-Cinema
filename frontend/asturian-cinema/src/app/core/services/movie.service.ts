import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie, CreateMovieRequest } from '../models/movie.model';
import { Review, CreateReviewRequest, PageResponse } from '../models/review.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private apiUrl = `${environment.apiUrl}/movies`;

  constructor(private http: HttpClient) {}

  getMovies(page: number = 0, size: number = 12, sort: string = 'createdAt,desc'): Observable<PageResponse<Movie>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort);
    
    return this.http.get<PageResponse<Movie>>(this.apiUrl, { params });
  }

  getAsturianMovies(page: number = 0, size: number = 12): Observable<PageResponse<Movie>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<PageResponse<Movie>>(`${this.apiUrl}/asturianas`, { params });
  }

  getMovie(id: number): Observable<Movie> {
    return this.http.get<Movie>(`${this.apiUrl}/${id}`);
  }

  searchMovies(query: string, page: number = 0, size: number = 12): Observable<PageResponse<Movie>> {
    const params = new HttpParams()
      .set('q', query)
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<PageResponse<Movie>>(`${this.apiUrl}/search`, { params });
  }

  getMoviesByGenre(genre: string, page: number = 0, size: number = 12): Observable<PageResponse<Movie>> {
    const params = new HttpParams()
      .set('genre', genre)
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<PageResponse<Movie>>(this.apiUrl, { params });
  }

  getTopRatedMovies(page: number = 0, size: number = 10): Observable<PageResponse<Movie>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', 'rating,desc');
    
    return this.http.get<PageResponse<Movie>>(this.apiUrl, { params });
  }

  createMovie(movie: CreateMovieRequest): Observable<Movie> {
    return this.http.post<Movie>(this.apiUrl, movie);
  }

  updateMovie(id: number, movie: CreateMovieRequest): Observable<Movie> {
    return this.http.put<Movie>(`${this.apiUrl}/${id}`, movie);
  }

  deleteMovie(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getMovieReviews(movieId: number, page: number = 0, size: number = 10): Observable<PageResponse<Review>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<PageResponse<Review>>(`${this.apiUrl}/${movieId}/reviews`, { params });
  }

  createReview(movieId: number, review: CreateReviewRequest): Observable<Review> {
    return this.http.post<Review>(`${this.apiUrl}/${movieId}/reviews`, review);
  }

  updateReview(reviewId: number, review: CreateReviewRequest): Observable<Review> {
    return this.http.put<Review>(`${environment.apiUrl}/reviews/${reviewId}`, review);
  }

  deleteReview(reviewId: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/reviews/${reviewId}`);
  }

  addToFavorites(movieId: number): Observable<void> {
    return this.http.post<void>(`${environment.apiUrl}/users/favorites/${movieId}`, {});
  }

  removeFromFavorites(movieId: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/users/favorites/${movieId}`);
  }

  getFavorites(page: number = 0, size: number = 12): Observable<PageResponse<Movie>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<PageResponse<Movie>>(`${environment.apiUrl}/users/favorites`, { params });
  }
}
