package ru.epipasha.academy

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import ru.epipasha.academy.data.Movie
import ru.epipasha.academy.db.MovieRepository

class MoviesListViewModel (
    private val context : Context
    ): ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val dbRepository = MovieRepository(context)

    private val _movies : MutableLiveData<List<Movie>> = MutableLiveData(emptyList())

    val movies : LiveData<List<Movie>> get() = _movies.also {
        loadMovies()
    }

    private fun loadMovies(){
        scope.launch {
//            var movieList = ru.epipasha.academy.data.loadMovies(context)
//            _movies.postValue(movieList)

            _movies.postValue(dbRepository.getAllMovies())

            try {
                val result = RetrofitModule.movieApi.getMovies().results.map {
                    Movie(
                        id = it.id,
                        title = it.title ?: "",
                        overview = it.overview,
                        poster = if (it.posterPath == null) "" else "https://image.tmdb.org/t/p/w500" + it.posterPath,
                        backdrop = if (it.backdropPath == null) "" else "https://image.tmdb.org/t/p/w500" + it.backdropPath,
                        ratings = (it.voteAverage ?: 0 / 2).toFloat(),
                        numberOfRatings = it.voteCount ?: 0,
                        minimumAge = if (it.adult ?: true) 18 else 13,
                        runtime = 100,
                        genres = emptyList(),
                        actors = emptyList()
                    )
                }

                dbRepository.deleteAllMovies()

                dbRepository.insertAllMovies(result)

                _movies.postValue(dbRepository.getAllMovies())
            }catch (e : Exception){
                e.printStackTrace()
            }

        }
    }
}

@Serializable
private data class CatImage(
        val id: String,
        @SerialName("url")
        val imageUrl: String
)

@Serializable
data class Result (
//        val page: Long,
        val results: List<MovieDTO>,
        //val totalResults: Long,
//        val totalPages: Long
)

@Serializable
data class MovieDTO (
        val posterPath: String? = null,
        val adult: Boolean? = true,
        val overview: String,
        val releaseDate: String? = null,
        val genreIDS: List<Long>? = null,
        val id: Long,
        val originalTitle: String? = "",
        val title: String? = "",
        val backdropPath: String? = null,
        val popularity: Double? = 0.0,
        val voteCount: Int? = 0,
        val video: Boolean? = false,
        val voteAverage: Double? = 0.0
)


private interface MovieApi {
    @GET("movie/popular?api_key=78c21450b0e26b58205c7ec792a9f233&language=en-US&page=1")
    suspend fun getMovies(): Result
}

private object RetrofitModule {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    val movieApi: MovieApi = retrofit.create()
}