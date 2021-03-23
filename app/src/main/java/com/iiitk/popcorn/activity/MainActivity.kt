package com.iiitk.popcorn.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.iiitk.popcorn.service.MovieService
import com.iiitk.popcorn.adapters.PopularMovieAdapter
import com.iiitk.popcorn.models.PopularMovies
import com.iiitk.popcorn.R
import com.iiitk.popcorn.database.FavDB
import com.iiitk.popcorn.database.FavDao
import com.iiitk.popcorn.database.FavEntity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter: PopularMovieAdapter
    lateinit var btnnext: Button
    lateinit var btnprev: Button
    lateinit var page:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        page=this.getSharedPreferences("page", MODE_PRIVATE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnnext=findViewById(R.id.btnNextPage)
        btnprev=findViewById(R.id.btnPrevPage)
        getmovies(1)
        page.edit().putInt("no",1).apply()
        btnnext.setOnClickListener {
            val currpage=page.getInt("no",1)
            getmovies(currpage+1)
            page.edit().putInt("no",currpage+1).apply()
        }
        btnprev.setOnClickListener {
            val currpage=page.getInt("no",1)
            if(currpage>1) {
                getmovies(currpage - 1)
                page.edit().putInt("no", currpage + 1).apply()
            }
        }

    }

    private fun getmovies( pg:Int) {
        val movies= MovieService.movieInstance.getMovies(pg)
        movies.enqueue(object: Callback<PopularMovies>{
            override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                val result=response.body()
                if (result != null) {
                    txtPageNo.text=result.page.toString()
                    adapter= PopularMovieAdapter(
                        this@MainActivity,
                        result.results
                    )
                    MovieList.adapter=adapter
                    MovieList.layoutManager=LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                Log.d("OUT","Error",t)
            }
        })
    }


}