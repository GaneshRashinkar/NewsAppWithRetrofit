package com.ganeshrashinkar.newsapplicationwithmvvm.models

import com.ganeshrashinkar.newsapplicationwithmvvm.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)