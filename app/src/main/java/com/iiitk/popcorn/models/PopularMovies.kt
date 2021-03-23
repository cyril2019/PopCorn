package com.iiitk.popcorn.models

import com.iiitk.popcorn.models.Movie

data class PopularMovies(val page:Int, val results: List<Movie>, val total_results:Int, val total_pages:Int)