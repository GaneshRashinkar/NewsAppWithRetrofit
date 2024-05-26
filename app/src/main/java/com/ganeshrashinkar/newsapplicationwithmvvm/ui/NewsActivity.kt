package com.ganeshrashinkar.newsapplicationwithmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ganeshrashinkar.newsapplicationwithmvvm.R
import com.ganeshrashinkar.newsapplicationwithmvvm.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    lateinit var mBinding:ActivityNewsBinding
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