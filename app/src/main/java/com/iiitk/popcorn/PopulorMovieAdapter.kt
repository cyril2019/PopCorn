package com.iiitk.popcorn

import android.content.Context
import android.view.LayoutInflater
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate
import com.squareup.picasso.Picasso

class PopularMovieAdapter(val context:Context,val movies: List<Movie>): RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder>()
{
    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val movieImg:ImageView=itemView.findViewById(R.id.imgMovImage)
        val movieName: TextView=itemView.findViewById(R.id.txtMovName)
        val movieRating: TextView=itemView.findViewById(R.id.txtMovRating)
        val movieDisc:TextView=itemView.findViewById(R.id.txtMovDiscription)
        val llMovie:LinearLayout=itemView.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.show_movie,parent,false)
        return  MovieViewHolder(view)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie=movies[position]
        Picasso.get().load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/${movie.poster_path}").error(R.mipmap.logo).into(holder.movieImg)
        holder.movieName.text=movie.title
        holder.movieRating.text=movie.vote_average.toString()
        holder.movieDisc.text=movie.overview
        holder.llMovie.setOnClickListener{
            val intent= Intent(context,MovieActivity::class.java)
            intent.putExtra("id",movie.id)
            intent.putExtra("name",movie.title)
            intent.putExtra("rating",movie.vote_average.toString())
            intent.putExtra("disc",movie.overview)
            intent.putExtra("image",movie.poster_path)
            intent.putExtra("backdrop",movie.backdrop_path)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}