package com.commcrete.omdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "movies")
@TypeConverters(RatingsConverter::class)
data class MovieItem(
    @PrimaryKey @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Rated") val rated: String? = null,
    @SerializedName("Released") val released: String? = null,
    @SerializedName("Runtime") val runtime: String? = null,
    @SerializedName("Genre") val genre: String? = null,
    @SerializedName("Director") val director: String? = null,
    @SerializedName("Writer") val writer: String? = null,
    @SerializedName("Actors") val actors: String? = null,
    @SerializedName("Plot") val plot: String? = null,
    @SerializedName("Language") val language: String? = null,
    @SerializedName("Country") val country: String? = null,
    @SerializedName("Awards") val awards: String? = null,
    @SerializedName("Metascore") val metascore: String? = null,
    @SerializedName("imdbRating") val imdbRating: String? = null,
    @SerializedName("imdbVotes") val imdbVotes: String? = null,
    @SerializedName("Type") val type: String? = null,
    @SerializedName("DVD") val dvd: String? = null,
    @SerializedName("BoxOffice") val boxOffice: String? = null,
    @SerializedName("Production") val production: String? = null,
    @SerializedName("Website") val website: String? = null,
    @SerializedName("Ratings") val ratings: List<Rating>? = null
)

data class Rating(
    @SerializedName("Source") val source: String,
    @SerializedName("Value") val value: String
)

class RatingsConverter {
    @TypeConverter
    fun fromRatingsList(ratings: List<Rating>?): String {
        return Gson().toJson(ratings)
    }

    @TypeConverter
    fun toRatingsList(ratingsJson: String?): List<Rating>? {
        return if (ratingsJson.isNullOrEmpty()) null
        else Gson().fromJson(ratingsJson, object : TypeToken<List<Rating>>() {}.type)
    }
}
