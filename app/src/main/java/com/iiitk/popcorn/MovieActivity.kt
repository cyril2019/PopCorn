package com.iiitk.popcorn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MovieActivity : AppCompatActivity() {
    val id:Int?=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        if(intent!=null){
            id=intent.getIntExtra("id")
        }
        else{
            Log.d("Error","error")
        }
    }
}