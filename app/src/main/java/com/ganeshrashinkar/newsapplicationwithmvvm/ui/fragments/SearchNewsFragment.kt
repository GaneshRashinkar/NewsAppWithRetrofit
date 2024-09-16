package com.ganeshrashinkar.newsapplicationwithmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganeshrashinkar.newsapplicationwithmvvm.Adapters.NewsAdapter
import com.ganeshrashinkar.newsapplicationwithmvvm.R
import com.ganeshrashinkar.newsapplicationwithmvvm.databinding.FragmentSearchNewsBinding
import com.ganeshrashinkar.newsapplicationwithmvvm.ui.NewsActivity
import com.ganeshrashinkar.newsapplicationwithmvvm.ui.NewsViewmodel
import com.ganeshrashinkar.newsapplicationwithmvvm.util.Constants
import com.ganeshrashinkar.newsapplicationwithmvvm.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment: Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel:NewsViewmodel
    lateinit var mBinding:FragmentSearchNewsBinding
    lateinit var newsAdapter:NewsAdapter
    val TAG="string"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        setupRecyclerView()
        newsAdapter.setOnItemClickListner {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        var job:Job?=null
        mBinding.etSearch.addTextChangedListener {editable->
            job?.cancel()
            job= MainScope().launch {
                delay(Constants.SEARCH_NEWS_DELAY)
                editable?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchNews(it.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse?.articles)
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let {message->
                        Log.e(TAG,"An error occured: $message")
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= FragmentSearchNewsBinding.inflate(layoutInflater)
        return mBinding.root
    }
    private fun showProgressBar(){
        mBinding.paginationProgressBar.visibility=View.VISIBLE
    }
    private fun hideProgressBar(){
        mBinding.paginationProgressBar.visibility=View.GONE
    }
    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        mBinding.rvSearchNews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
}