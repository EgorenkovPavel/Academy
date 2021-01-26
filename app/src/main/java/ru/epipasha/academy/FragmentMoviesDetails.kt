package ru.epipasha.academy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.epipasha.academy.data.Actor
import ru.epipasha.academy.data.Movie

class FragmentMoviesDetails(val movie: Movie) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

        val btnBack : Button? = view?.findViewById(R.id.btn_back)
        btnBack?.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        view?.findViewById<TextView>(R.id.tv_age_limit)?.text = "${movie.minimumAge}+"
        view?.findViewById<TextView>(R.id.tv_title)?.text = movie.title
        view?.findViewById<TextView>(R.id.tv_genre)?.text = movie.genres.map { it.name }.joinToString { ","}
        view?.findViewById<RatingBar>(R.id.ratingBar)?.rating = movie.ratings / 2
        view?.findViewById<TextView>(R.id.tv_views_count)?.text = "${movie.numberOfRatings} REVIEWS"
        view?.findViewById<TextView>(R.id.tv_storyline_description)?.text = movie.overview

        view?.findViewById<ImageView>(R.id.img_poster)?.let {
            Glide.with(this).load(movie.backdrop).into(
                it
            )
        }

        val rvActors : RecyclerView? = view?.findViewById(R.id.rv_actors)

        rvActors?.adapter = ActorsAdapter(movie.actors)

        rvActors?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        return view
    }
}

class ActorsAdapter(val actors : List<Actor>) : RecyclerView.Adapter<ActorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(actors[position])
    }

    override fun getItemCount(): Int {
        return actors.size
    }

}

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvActor : TextView = itemView.findViewById(R.id.tv_actor)
    private val imgActor : ImageView = itemView.findViewById(R.id.img_actor)

    fun onBind(actor : Actor){
        tvActor.text = actor.name
        Glide.with(imgActor).load(actor.picture).into(imgActor)
    }

}