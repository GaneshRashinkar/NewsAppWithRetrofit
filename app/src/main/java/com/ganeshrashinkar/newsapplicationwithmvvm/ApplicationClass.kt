package com.ganeshrashinkar.newsapplicationwithmvvm

import android.app.Application
import com.ganeshrashinkar.newsapplicationwithmvvm.db.ArticleDatabase
import com.ganeshrashinkar.newsapplicationwithmvvm.repository.NewsRepository

class ApplicationClass: Application() {
    val database by lazy { ArticleDatabase.getDatabase(this) }
    val repository by lazy{ NewsRepository(database.getArticleDao())}
}