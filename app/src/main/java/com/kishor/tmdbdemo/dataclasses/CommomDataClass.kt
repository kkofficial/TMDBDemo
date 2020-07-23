package com.kishor.tmdbdemo.dataclasses

data class MovieResponse(
    val page: Int,
    val results: List<Results>,
    val total_pages: Int,
    val total_results: Int
)

data class Results(
    val title: String,
    val release_date: String,
    val overview: String
)