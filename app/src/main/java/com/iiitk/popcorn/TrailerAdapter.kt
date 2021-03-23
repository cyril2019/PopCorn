package com.iiitk.popcorn

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.internal.s

class TrailerAdapter(val context:Context,val trailers:List<Vedio>):RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>(){
    class TrailerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val player:YouTubePlayerView=itemView.findViewById(R.id.Player)
        lateinit var initializer:YouTubePlayer.OnInitializedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_trailer,parent,false)
        return TrailerAdapter.TrailerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {


        val vedioId=trailers[position]
        val API_YOUTUBE="AIzaSyBSMmjdkx9iHMxH6f0DdffNEoSN9TdWdKA"
        holder.initializer=object :YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                Player: YouTubePlayer?,
                p2: Boolean
            ) {
                Player?.loadVideo(vedioId.key)
                Log.d("OUT","entered Loading vedios ***********")
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.d("OUT","----Not loaded Vedio $p1")
            }

        }
        holder.player.setOnClickListener{
            holder.player.initialize(API_YOUTUBE,holder.initializer)
        }
    }
}