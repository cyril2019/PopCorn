package com.iiitk.popcorn.service

import com.iiitk.popcorn.models.PopularMovies
import com.iiitk.popcorn.models.ReviewList
import com.iiitk.popcorn.models.VedioList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL="https://api.themoviedb.org/3/"
const val API_KEY="4e44d9029b1270a757cddc766a1bcb63"
interface  MovieInterface{
    @GET("movie/popular?api_key=$API_KEY")
    fun getMovies(@Query("page") page:Int): Call<PopularMovies>
    @GET("movie/{movie_id}/reviews?api_key=$API_KEY")
    fun getReviews(@Path("movie_id",encoded = true) id: String): Call<ReviewList>
    @GET("movie/{movie_id}/videos?api_key=$API_KEY")
    fun getTrailer(@Path("movie_id",encoded = true) id:String):Call<VedioList>
}

object MovieService{
    val movieInstance: MovieInterface
    init {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        movieInstance =retrofit.create(
            MovieInterface::class.java)
    }
}