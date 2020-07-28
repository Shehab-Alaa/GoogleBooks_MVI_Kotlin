package com.example.googlebookscleanarchitecture.view.main

import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.databinding.ActivityMainBinding
import com.example.googlebookscleanarchitecture.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}