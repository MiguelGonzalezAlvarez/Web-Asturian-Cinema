export interface Review {
  id: number;
  rating: number;
  comment: string;
  isEdited: boolean;
  createdAt: string;
  updatedAt: string;
  user: any;
  movieId: number;
}

export interface CreateReviewRequest {
  rating: number;
  comment: string;
}

export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
}
