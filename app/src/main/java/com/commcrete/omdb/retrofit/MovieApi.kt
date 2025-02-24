package com.commcrete.omdb.retrofit

import com.commcrete.omdb.BuildConfig
import com.commcrete.omdb.retrofit.response.MovieResponse
import com.commcrete.omdb.retrofit.response.MoviesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): MoviesResponse

    @GET("/")
    suspend fun searchMovie(
        @Query("i") query: String,
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): MovieResponse
}

object RetrofitInstance {
    val api: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}
