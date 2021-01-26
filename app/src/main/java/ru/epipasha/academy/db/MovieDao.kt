package ru.epipasha.academy.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getAll() : List<MovieEntity>

    @Insert
    suspend fun insertAll(movies : List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}