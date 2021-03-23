package com.iiitk.popcorn.adapters

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iiitk.popcorn.R
import com.iiitk.popcorn.models.Review

class ReviewAdapter(val context:Context,val reviews:List<Review>):RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.single_review,parent,false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review=reviews[position]
        holder.authorName.text=review.author
        holder.content.text=review.content
        holder.content.movementMethod = ScrollingMovementMethod()

    }

    override fun getItemCount(): Int {
        return reviews.size
    }
    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorName:TextView=itemView.findViewById(R.id.txtAuthorName)
        val content:TextView=itemView.findViewById(R.id.txtAuthorContent)
    }
}