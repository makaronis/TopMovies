package com.makaroni.topmovies.data;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MovieDbApi {
    @GET("discover/movie")
    Call<MovieResponse> loadMovies(@QueryMap Map<String,String> call);
}
