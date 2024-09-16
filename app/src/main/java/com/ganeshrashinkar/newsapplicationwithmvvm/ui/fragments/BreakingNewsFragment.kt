package com.ganeshrashinkar.newsapplicationwithmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganeshrashinkar.newsapplicationwithmvvm.Adapters.NewsAdapter
import com.ganeshrashinkar.newsapplicationwithmvvm.R
import com.ganeshrashinkar.newsapplicationwithmvvm.databinding.FragmentBreakingNewsBinding
import com.ganeshrashinkar.newsapplicationwithmvvm.ui.NewsActivity
import com.ganeshrashinkar.newsapplicationwithmvvm.ui.NewsViewmodel
import com.ganeshrashinkar.newsapplicationwithmvvm.util.Resource

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {
lateinit var viewModel:NewsViewmodel
lateinit var newsAdapter:NewsAdapter
lateinit var mBinding:FragmentBreakingNewsBinding
val TAG="myTag"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= FragmentBreakingNewsBinding.inflate(inflater)
        return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        setupRecyclerView()
        newsAdapter.setOnItemClickListner {
            Log.e("article onClick","$it")
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            Log.e("article onClick","$bundle")
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        viewModel.breakingNews.observe(viewLifecycleOwner){response->
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

    private fun showProgressBar(){
        mBinding.paginationProgressBar.visibility=View.VISIBLE
    }
    private fun hideProgressBar(){
        mBinding.paginationProgressBar.visibility=View.GONE
    }
    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        mBinding.rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
        }
    }
}