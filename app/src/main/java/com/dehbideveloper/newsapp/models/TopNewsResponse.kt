package com.dehbideveloper.newsapp.models

data class TopNewsResponse(
     val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<TopNewsArticle>? = null
)
