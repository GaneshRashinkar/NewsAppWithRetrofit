package com.ganeshrashinkar.newsapplicationwithmvvm.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.ganeshrashinkar.newsapplicationwithmvvm.databinding.ItemArticlePreviewBinding
import com.ganeshrashinkar.newsapplicationwithmvvm.Article

class NewsAdapter:Adapter<NewsAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(var articlePreviewBinding: ItemArticlePreviewBinding):ViewHolder(articlePreviewBinding.root)

    private val differCallback=object :DiffUtil.ItemCallback<Article>(){
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.url==newItem.url
        }

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }

    val differ=AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding=ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        holder.articlePreviewBinding.root.apply {
            val article=differ.currentList[position]
            Log.e("article","$article")
            Glide.with(this).load(article.urlToImage).into(holder.articlePreviewBinding.ivArticleImage)
            holder.articlePreviewBinding.tvSource.text=article.source.name
            holder.articlePreviewBinding.tvTitle.text=article.title
            holder.articlePreviewBinding.tvDescription.text=article.description
            holder.articlePreviewBinding.tvPublishedAt.text=article.publishedAt
            holder.articlePreviewBinding.root.setOnClickListener {
                onItemClickListner?.let {
                    Log.e("article onClick","$article")
                    it(article)
                }
            }
        }

    }

    private var onItemClickListner:((Article)->Unit)? = null

    fun setOnItemClickListner(listner:(Article)->Unit){
        onItemClickListner=listner
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}