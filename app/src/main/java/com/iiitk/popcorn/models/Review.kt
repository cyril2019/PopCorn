package com.iiitk.popcorn.models

import com.iiitk.popcorn.models.Author

data class Review(val author:String,
                  val author_details: Author,
                  val content:String,
                  val created_at:String,
                  val id:String,
                  val updated_at:String,
                  val url:String,
                  val total_pages:Int,
                  val total_result:Int)