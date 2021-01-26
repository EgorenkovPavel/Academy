package ru.epipasha.academy.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.epipasha.academy.data.Movie

class MovieRepository(
    applicationContext: Context
) {
    private val db = MovieDatabase.create(applicationContext)

    suspend fun getAllMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            db.movieDao.getAll().map {
                   Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        poster = "",
                        backdrop = "",
                        ratings = 0f,
                        numberOfRatings = 0,
                        minimumAge = 13,
                        runtime = 0,
                        genres = emptyList(),
                        actors = emptyList()
                    )
            }
        }
    }

    suspend fun insertAllMovies(movies: List<Movie>) {
        withContext(Dispatchers.IO) {
            db.movieDao.insertAll(movies.map {
                MovieEntity(
                    id = it.id,
                    title = it.title,
                    overview = it.overview
                )
            })
        }
    }

    suspend fun deleteAllMovies(){
        withContext(Dispatchers.IO){
            db.movieDao.deleteAll()
        }
    }
}