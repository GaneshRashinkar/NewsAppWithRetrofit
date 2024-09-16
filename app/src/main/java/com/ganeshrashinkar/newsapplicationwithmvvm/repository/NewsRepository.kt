package com.ganeshrashinkar.newsapplicationwithmvvm.repository

import com.ganeshrashinkar.newsapplicationwithmvvm.api.RetrofitInstance
import com.ganeshrashinkar.newsapplicationwithmvvm.db.ArticleDatabase
import com.ganeshrashinkar.newsapplicationwithmvvm.Article
import com.ganeshrashinkar.newsapplicationwithmvvm.db.ArticleDao

class NewsRepository(
    val articleDao: ArticleDao
) {
    suspend fun getBreakingNews(countryCode:String,pageNumber:Int) =
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery:String, pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

    suspend fun upsert(article: Article)=articleDao.upsert(article)

    fun getSavedNews() = articleDao.getAllArticles()

    suspend fun deleteArticle(article: Article)=articleDao.deleteArticle(article)


}