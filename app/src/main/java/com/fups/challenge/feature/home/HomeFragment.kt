package com.fups.challenge.feature.home

import com.fups.challenge.R
import com.fups.challenge.core.platform.BaseFragment
import com.fups.challenge.databinding.FragmentHomeBinding
import com.fups.challenge.feature.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>(
    layoutId = R.layout.fragment_home,
    MainViewModel::class.java
) {
    override fun getScreenKey(): String {
        return "Home Page"
    }


    override fun onDataBinding() {

    }


}