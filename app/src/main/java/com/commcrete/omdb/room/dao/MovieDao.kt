package com.commcrete.omdb.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.commcrete.omdb.model.MovieItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE Title LIKE '%' || :query || '%'")
    fun searchMovies(query: String): Flow<List<MovieItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieItem)

    @Query("SELECT * FROM movies WHERE imdbID = :imdbID")
    fun getMovieById(imdbID: String): Flow<MovieItem?>
}
