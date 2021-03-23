package com.iiitk.popcorn

data class PopularMovies(val page:Int, val results: List<Movie>, val total_results:Int, val total_pages:Int)