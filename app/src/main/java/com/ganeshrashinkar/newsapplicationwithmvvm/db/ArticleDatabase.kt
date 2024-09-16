package com.ganeshrashinkar.newsapplicationwithmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ganeshrashinkar.newsapplicationwithmvvm.Article


@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null
        private val LOCK = Any()

        fun getDatabase(context: Context):ArticleDatabase{
            return INSTANCE?: synchronized(this){
                val instanse=Room.databaseBuilder(context.applicationContext,
                    ArticleDatabase::class.java,
                    "article_database"
                    ).build()
                INSTANCE=instanse
                instanse
            }
        }

    }
}