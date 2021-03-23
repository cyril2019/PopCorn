package com.iiitk.popcorn.models

data class ReviewList(val page:Int, val results: List<Review>, val total_results:Int, val total_pages:Int)