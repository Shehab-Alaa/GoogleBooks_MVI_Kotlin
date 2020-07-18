package com.example.googlebookscleanarchitecture.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.databinding.ActivityMainBinding
import com.example.googlebookscleanarchitecture.view.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}