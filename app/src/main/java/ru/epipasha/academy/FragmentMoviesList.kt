package ru.epipasha.academy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.epipasha.academy.data.Movie
import ru.epipasha.academy.data.loadMovies

class FragmentMoviesList : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val factory = MoviesListViewModelFactory(requireActivity().application)
        val viewModel : MoviesListViewModel = factory.create(MoviesListViewModel::class.java)

        val view: View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

        val rvMovies: RecyclerView? = view?.findViewById<RecyclerView>(R.id.rv_movies)

        viewModel.movies.observe(this, Observer {
            rvMovies?.adapter = MoviesAdapter(it, requireActivity() as OnMovieClickListener)
        })

        rvMovies?.layoutManager = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)

        return view
    }

}

class MoviesAdapter(
    private val movieList: List<Movie>,
    private val clickListener: OnMovieClickListener
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movieList[position])
        holder.itemView.setOnClickListener {
            clickListener.onMovieClick(movieList[position])
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    private val tvAgeLimit: TextView = itemView.findViewById(R.id.tv_age_limit)
    private val imgPoster: ImageView = itemView.findViewById(R.id.img_poster)
    private val tvGenre: TextView = itemView.findViewById(R.id.tv_genre)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
    private val viewsCount: TextView = itemView.findViewById(R.id.tv_views_count)
    private val tvDuration: TextView = itemView.findViewById(R.id.tv_duration)

    fun onBind(movie: Movie) {
        tvTitle.text = movie.title
        tvAgeLimit.text = "${movie.minimumAge}+"
        //imgPoster.setBackgroundResource(movie.imgPoster)
        tvGenre.text = movie.genres.map { it.name }.joinToString { ","}
        ratingBar.rating = movie.ratings / 2
        viewsCount.text = "${movie.numberOfRatings} REVIEWS"
        tvDuration.text = "${movie.runtime} MIN"

        Glide.with(itemView).load(movie.poster).into(imgPoster)
        print(movie.poster)
    }
}
