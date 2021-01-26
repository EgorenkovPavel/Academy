package ru.epipasha.academy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.epipasha.academy.data.Movie
import ru.epipasha.academy.data.loadMovies

class MainActivity : AppCompatActivity(), OnMovieClickListener {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FragmentMoviesList())
                .commit()
        }
    }

    override fun onMovieClick(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FragmentMoviesDetails(movie))
            .addToBackStack(null)
            .commit()
    }

}

interface OnMovieClickListener {
    fun onMovieClick(movie: Movie)
}

