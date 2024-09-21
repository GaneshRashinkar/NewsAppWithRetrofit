package com.ganeshrashinkar.newsapplicationwithmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ganeshrashinkar.newsapplicationwithmvvm.Adapters.NewsAdapter
import com.ganeshrashinkar.newsapplicationwithmvvm.R
import com.ganeshrashinkar.newsapplicationwithmvvm.databinding.FragmentBreakingNewsBinding
import com.ganeshrashinkar.newsapplicationwithmvvm.ui.NewsActivity
import com.ganeshrashinkar.newsapplicationwithmvvm.ui.NewsViewmodel
import com.ganeshrashinkar.newsapplicationwithmvvm.util.Constants.Companion.QUERY_PAGE_SIZE
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
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages=newsResponse.totalResults/ QUERY_PAGE_SIZE+2
                        isLastPage=viewModel.breakingNewsPage == totalPages
                        if(isLastPage){
                            mBinding.rvBreakingNews.setPadding(0,0,0,0)
                        }
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
        isLoading=true
    }
    private fun hideProgressBar(){
        mBinding.paginationProgressBar.visibility=View.GONE
        isLoading=false
    }

    var isLoading:Boolean=false
    var isLastPage=false
    var isScrolling=false

    val scrollListner=object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager=recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition=layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount=layoutManager.childCount
            val totalItemCount=layoutManager.itemCount

            val isNotLoadingAndNotLastPage= !isLoading && !isLastPage
            val isAtLastItem= firstVisibleItemPosition+visibleItemCount>=totalItemCount
            val isNotAtBegginning= firstVisibleItemPosition>0
            val isTotalMoreThanVisible=totalItemCount>=QUERY_PAGE_SIZE

            val shouldPaginate=isNotLoadingAndNotLastPage  && isAtLastItem && isNotAtBegginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.getBreakingNews("us")
                isScrolling=false
            }
        }
    }
    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        mBinding.rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListner)
        }
    }
}