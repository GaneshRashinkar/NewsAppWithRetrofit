package com.ganeshrashinkar.newsapplicationwithmvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ganeshrashinkar.newsapplicationwithmvvm.R
import com.ganeshrashinkar.newsapplicationwithmvvm.databinding.FragmentArticleBinding
import com.ganeshrashinkar.newsapplicationwithmvvm.ui.NewsActivity
import com.ganeshrashinkar.newsapplicationwithmvvm.ui.NewsViewmodel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment: Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel:NewsViewmodel
    lateinit var mBinding:FragmentArticleBinding
    val args:ArticleFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        var article=args.article
        mBinding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }
        mBinding.fab.setOnClickListener {
            article.id=0
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved Successfully",Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= FragmentArticleBinding.inflate(layoutInflater)
        return mBinding.root
    }
}