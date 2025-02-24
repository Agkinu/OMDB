package com.commcrete.omdb.retrofit.response

import com.commcrete.omdb.model.MovieItem
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("Search") val search: List<MovieItem>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String?
)
