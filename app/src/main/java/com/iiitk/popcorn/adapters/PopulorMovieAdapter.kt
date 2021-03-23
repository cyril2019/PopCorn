package com.iiitk.popcorn.adapters

import android.content.Context
import android.view.LayoutInflater
import android.content.Intent
import android.os.AsyncTask
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.iiitk.popcorn.R
import com.iiitk.popcorn.activity.MovieActivity
import com.iiitk.popcorn.database.FavDB
import com.iiitk.popcorn.database.FavEntity
import com.iiitk.popcorn.models.Movie
import com.squareup.picasso.Picasso

class PopularMovieAdapter(val context:Context,val movies: List<Movie>): RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder>()
{
    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val movieImg:ImageView=itemView.findViewById(R.id.imgMovImage)
        val movieName: TextView=itemView.findViewById(R.id.txtMovName)
        val movieRating: TextView=itemView.findViewById(R.id.txtMovRating)
        val movieDisc:TextView=itemView.findViewById(R.id.txtMovDiscription)
        val llMovie:LinearLayout=itemView.findViewById(R.id.llContent)
        val imgIsFav:ImageView=itemView.findViewById(R.id.imgIsFav)
        val imgNotFav:ImageView=itemView.findViewById(R.id.imgNotFav)
        val fav:RelativeLayout=itemView.findViewById(R.id.fav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.show_movie,parent,false)
        return MovieViewHolder(
            view
        )
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie=movies[position]
        Picasso.get().load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/${movie.poster_path}").error(
            R.mipmap.logo
        ).into(holder.movieImg)
        holder.movieName.text=movie.title
        holder.movieRating.text=movie.vote_average.toString()
        holder.movieDisc.text=movie.overview
        holder.llMovie.setOnClickListener{
            val intent= Intent(context,
                MovieActivity::class.java)
            intent.putExtra("id",movie.id)
            intent.putExtra("name",movie.title)
            intent.putExtra("rating",movie.vote_average.toString())
            intent.putExtra("disc",movie.overview)
            intent.putExtra("image",movie.poster_path)
            intent.putExtra("backdrop",movie.backdrop_path)
            context.startActivity(intent)
        }
        val favEntity=FavEntity(movie.id,movie.title.toString())
        val checkFav=DBAsyncTask(context,favEntity,1).execute()
        val isFav=checkFav.get()
        if(isFav){
            holder.imgIsFav.visibility=View.VISIBLE
            holder.imgNotFav.visibility=View.INVISIBLE
        }
        else{
            holder.imgIsFav.visibility=View.INVISIBLE
            holder.imgNotFav.visibility=View.VISIBLE
        }
        holder.fav.setOnClickListener{
            if(DBAsyncTask(context,favEntity,1).execute().get()){
                val remFav=DBAsyncTask(context,favEntity,3).execute()
                val result=remFav.get()
                holder.imgIsFav.visibility=View.INVISIBLE
                holder.imgNotFav.visibility=View.VISIBLE
                if(result){
                    Toast.makeText(
                        context,
                        " removed",
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.imgIsFav.visibility=View.INVISIBLE
                    holder.imgNotFav.visibility=View.VISIBLE
                }
                else{
                    Toast.makeText(
                        context,
                        "some error occured",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else{
                val addFav=DBAsyncTask(context,favEntity,2).execute()
                val result=addFav.get()
                holder.imgIsFav.visibility=View.VISIBLE
                holder.imgNotFav.visibility=View.INVISIBLE
                if(result){
                    Toast.makeText(
                        context,
                        "added",
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.imgIsFav.visibility=View.VISIBLE
                    holder.imgNotFav.visibility=View.INVISIBLE
                }
                else{
                    Toast.makeText(
                        context,
                        "some error occured",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return movies.size
    }
    class DBAsyncTask(val context: Context, val favourite: FavEntity, val mode:Int):
        AsyncTask<Void, Void, Boolean>(){
        val db= Room.databaseBuilder(context, FavDB::class.java,"favdb").build()

        override fun doInBackground(vararg p0: Void?): Boolean {
            when(mode){
                1->{
                    val movie: FavEntity =db.favDao().getMovie(favourite.favId)
                    db.close()
                    return movie!=null
                }
                2->{
                    db.favDao().addFav(favourite)
                    db.close()
                    return true
                }
                3->{
                    db.favDao().remFav(favourite)
                    db.close()
                    return true
                }
            }
            return false
        }

    }
}

