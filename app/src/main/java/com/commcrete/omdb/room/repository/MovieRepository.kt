package com.commcrete.omdb.room.repository

import androidx.lifecycle.LiveData
import com.commcrete.omdb.model.MovieItem
import com.commcrete.omdb.retrofit.MovieApi
import com.commcrete.omdb.room.dao.MovieDao
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val api: MovieApi, private val dao: MovieDao) {

    suspend fun searchMovie(movieId: String) {
        val response = api.searchMovie(movieId)
        val movieItem = response.toMovieItem()
        dao.insertMovie(movieItem)
    }

    suspend fun searchMovies(query: String) {
        val response = api.searchMovies(query)
        response.search?.let {
            dao.insertMovies(it)
        }
    }

    fun getMoviesFromDb(query: String): Flow<List<MovieItem>> {
        return dao.searchMovies(query)
    }

    fun getMovieById(imdbID: String): Flow<MovieItem?> {
        return dao.getMovieById(imdbID)
    }

}
