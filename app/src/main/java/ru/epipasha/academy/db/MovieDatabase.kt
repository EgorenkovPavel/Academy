package ru.epipasha.academy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    companion object {

        fun create(applicationContext: Context): MovieDatabase = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            "Movies.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}