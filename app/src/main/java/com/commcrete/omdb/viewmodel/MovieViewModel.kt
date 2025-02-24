package com.commcrete.omdb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.commcrete.omdb.model.MovieItem
import com.commcrete.omdb.room.repository.MovieRepository
import com.commcrete.omdb.utils.NetworkUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository, private val app: Application) : ViewModel() {

    private var selectedMovieJob: Job? = null
    private var searchMoviesJob: Job? = null

    private val searchQuery = MutableLiveData<String>()
    private val _movies = MediatorLiveData<List<MovieItem>>()
    val movies: LiveData<List<MovieItem>> get() = _movies

    private val _selectedMovie = MutableStateFlow<MovieItem?>(null)
    val selectedMovie: StateFlow<MovieItem?> get() = _selectedMovie.asStateFlow()

    fun setSearchQuery(query: String) {
        searchQuery.value = query

        searchMoviesJob?.cancel()
        searchMoviesJob = viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(app)) {
                repository.searchMovies(query)
            }
            repository.getMoviesFromDb(query).collectLatest { moviesFromDb ->
                _movies.postValue(moviesFromDb)
            }
        }
    }


    fun selectMovie(movie: MovieItem) {
        _selectedMovie.value = movie
        selectedMovieJob?.cancel()
        selectedMovieJob = viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(app)) {
                repository.searchMovie(movie.imdbID)
            }

            repository.getMovieById(movie.imdbID).firstOrNull()?.let { updatedMovie ->
                _selectedMovie.value = updatedMovie
            }
        }


    }

    class MovieViewModelFactory(private val repository: MovieRepository, private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(repository, app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
