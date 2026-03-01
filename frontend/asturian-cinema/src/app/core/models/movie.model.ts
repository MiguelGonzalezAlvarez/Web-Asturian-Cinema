export interface Movie {
  id: number;
  title: string;
  originalTitle: string;
  synopsis: string;
  director: string;
  year: number;
  duration: number;
  genre: string;
  coverImage: string;
  backdropImage: string;
  trailerUrl: string;
  movieUrl: string;
  isAsturian: boolean;
  origin: string;
  language: string;
  cast: string;
  productionCompany: string;
  filmingLocations: string;
  latitude: number;
  longitude: number;
  published: boolean;
  averageRating: number;
  reviewCount: number;
  views: number;
  createdAt: string;
  createdBy: any;
  isFavorite: boolean;
}

export interface CreateMovieRequest {
  title: string;
  originalTitle?: string;
  synopsis?: string;
  director?: string;
  year?: number;
  duration?: number;
  genre?: string;
  coverImage?: string;
  backdropImage?: string;
  trailerUrl?: string;
  movieUrl?: string;
  isAsturian?: boolean;
  origin?: string;
  language?: string;
  cast?: string;
  productionCompany?: string;
  filmingLocations?: string;
  latitude?: number;
  longitude?: number;
  published?: boolean;
}
