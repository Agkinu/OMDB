package com.commcrete.omdb.room.database

import android.content.Context
import androidx.room.*
import com.commcrete.omdb.model.MovieItem
import com.commcrete.omdb.room.dao.MovieDao

@Database(entities = [MovieItem::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}
