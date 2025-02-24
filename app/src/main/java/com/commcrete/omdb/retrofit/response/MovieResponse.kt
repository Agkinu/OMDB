package com.commcrete.omdb.retrofit.response

import com.commcrete.omdb.model.MovieItem
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String?,
    @SerializedName("Rated") val rated: String? = "N/A",
    @SerializedName("Released") val released: String? = "N/A",
    @SerializedName("Runtime") val runtime: String? = "N/A",
    @SerializedName("Genre") val genre: String? = "N/A",
    @SerializedName("Director") val director: String? = "N/A",
    @SerializedName("Writer") val writer: String? = "N/A",
    @SerializedName("Actors") val actors: String? = "N/A",
    @SerializedName("Plot") val plot: String? = "N/A",
    @SerializedName("Language") val language: String? = "N/A",
    @SerializedName("Country") val country: String? = "N/A",
    @SerializedName("Awards") val awards: String? = "N/A",
    @SerializedName("Metascore") val metascore: String? = "N/A",
    @SerializedName("imdbRating") val imdbRating: String? = "N/A",
    @SerializedName("imdbVotes") val imdbVotes: String? = "N/A",
    @SerializedName("Type") val type: String? = "N/A",
    @SerializedName("DVD") val dvd: String? = "N/A",
    @SerializedName("BoxOffice") val boxOffice: String? = "N/A",
    @SerializedName("Production") val production: String? = "N/A",
    @SerializedName("Website") val website: String? = "N/A"
) {
    fun toMovieItem(): MovieItem {
        return MovieItem(
            imdbID = imdbID,
            title = title,
            year = year,
            poster = poster ?: "",
            rated = rated,
            released = released,
            runtime = runtime,
            genre = genre,
            director = director,
            writer = writer,
            actors = actors,
            plot = plot,
            language = language,
            country = country,
            awards = awards,
            metascore = metascore,
            imdbRating = imdbRating,
            imdbVotes = imdbVotes,
            type = type,
            dvd = dvd,
            boxOffice = boxOffice,
            production = production,
            website = website
        )
    }
}
