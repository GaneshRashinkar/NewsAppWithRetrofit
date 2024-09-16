package com.ganeshrashinkar.newsapplicationwithmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ganeshrashinkar.newsapplicationwithmvvm.ApplicationClass
import com.ganeshrashinkar.newsapplicationwithmvvm.R
import com.ganeshrashinkar.newsapplicationwithmvvm.databinding.ActivityNewsBinding

class NewsActivity : FragmentActivity() {
    lateinit var mBinding:ActivityNewsBinding
     val viewModel:NewsViewmodel by viewModels { NewsViewmodelProviderFactory((application as ApplicationClass).repository) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityNewsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
       // val navController=findNavController(R.id.newsNavHostFragment)
        val navHostFragment=mBinding.root.findViewById<View>(R.id.newsNavHostFragment)
        val navController=navHostFragment.findNavController()
        mBinding.bottomNavigationView.setupWithNavController(navController)


    }
}