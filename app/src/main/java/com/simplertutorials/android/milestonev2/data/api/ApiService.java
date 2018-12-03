package com.simplertutorials.android.milestonev2.data.api;

import com.simplertutorials.android.milestonev2.domain.GenresResponse;
import com.simplertutorials.android.milestonev2.domain.Movie;
import com.simplertutorials.android.milestonev2.domain.PopularMoviesResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/3/genre/movie/list")
    Call<GenresResponse> fetchGenres(@Query("api_key") String apiKey,
                                     @Query("language") String language);

    @GET("3/movie/popular")
    Single<PopularMoviesResponse> getPopularMovies(@Query("api_key") String apiKey,
                                                   @Query("language") String language,
                                                   @Query("page") Integer page);

    @GET("3/movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") String movieId,
                                @Query("api_key") String apiKey,
                                @Query("language") String language);
}
