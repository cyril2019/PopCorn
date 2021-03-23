package com.iiitk.popcorn.activity

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.iiitk.popcorn.*
import com.iiitk.popcorn.adapters.ReviewAdapter
import com.iiitk.popcorn.adapters.TrailerAdapter
import com.iiitk.popcorn.models.ReviewList
import com.iiitk.popcorn.models.VedioList
import com.iiitk.popcorn.service.MovieService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


 var id:Int?=1
private var name:String?="movie"
private var rating:String?="5.0"
private var disc:String?="disc"
private var image:String?="image"
private var backdrop:String?="back"
lateinit var movieicon:ImageView
lateinit var adapter: ReviewAdapter
lateinit var adapter2: TrailerAdapter
class MovieActivity : YouTubeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        movieicon =findViewById(R.id.imgMovieImage)
        if(intent!=null){
            id =intent.getIntExtra("id", 1)
            name =intent.getStringExtra("name")
            txtMovieName.text= name
            rating =intent.getStringExtra("rating")
            txtMovieRating.text= rating
            disc =intent.getStringExtra("disc")
            txtMovieDisc.text= disc
            txtMovieDisc.movementMethod = ScrollingMovementMethod()
            image =intent.getStringExtra("image")
            Picasso.get().load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/$image").error(
                R.mipmap.logo
            ).into(
                movieicon
            )
            backdrop =intent.getStringExtra("backdrop")
        }
        else {
            Log.d("OUT", "Didnt recieve data")
        }
        getReviews()
        getTrailer()
    }

    private fun getTrailer() {
        val vedios= MovieService.movieInstance.getTrailer(
            id.toString())
        vedios.enqueue(object:Callback<VedioList>{
            override fun onFailure(call: Call<VedioList>, t: Throwable) {
                Log.d("OUT","Failed",t)
            }

            override fun onResponse(call: Call<VedioList>, response: Response<VedioList>) {
                val result=response.body()
                if (result!=null){
                    Log.d("OUT","Success1------------")
                    val trailers=result.results

                    adapter2 =
                        TrailerAdapter(
                            this@MovieActivity,
                            trailers
                        )
                    rvTrailer.adapter= adapter2
                    rvTrailer.layoutManager=LinearLayoutManager(this@MovieActivity,LinearLayoutManager.HORIZONTAL,false)
                }
                else{
                    Log.d("OUT","NULL recieved at vedio $id")
                }
            }
        })
    }

    private fun getReviews() {
        Log.d("OUT","Entered getReviews")
        val reviews= MovieService.movieInstance.getReviews(
            id.toString())
        reviews.enqueue(object:Callback<ReviewList>{
            override fun onResponse(call: Call<ReviewList>, response: Response<ReviewList>) {
                val result=response.body()
                if (result!=null){
                    Log.d("OUT","Success2------------")
                    val reviews=result.results
                    adapter =
                        ReviewAdapter(
                            this@MovieActivity,
                            reviews
                        )
                    rvReviews.adapter= adapter
                    rvReviews.layoutManager=LinearLayoutManager(this@MovieActivity,LinearLayoutManager.HORIZONTAL,false)
                }
                else{
                    Log.d("OUT","NULL recieved $id")
                }
            }

            override fun onFailure(call: Call<ReviewList>, t: Throwable) {
                Log.d("OUT","Error 2",t)
            }
        })
    }
}