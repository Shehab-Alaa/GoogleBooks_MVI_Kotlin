package com.example.googlebookscleanarchitecture.view.main.book_details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.transition.TransitionInflater
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.example.googlebookscleanarchitecture.databinding.FragmentBookDetailsBinding
import com.example.googlebookscleanarchitecture.view.base.BaseFragment


class BookDetailsFragment : BaseFragment<FragmentBookDetailsBinding>(){

    private lateinit var book : Book

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initToolbar()
        book = BookDetailsFragmentArgs.fromBundle(requireArguments()).selectedBook
        getViewDataBinding().book = book
        return getMRootView()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewDataBinding().bookThumbnail.transitionName = book.bookInfo?.imageLinks?.thumbnail
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override val layoutId: Int
        get() = R.layout.fragment_book_details

    private fun initToolbar() {
        val layout = getViewDataBinding().collapsingToolbar
        val toolbar = getViewDataBinding().toolbar
        val navController =
            activity?.let { Navigation.findNavController(it,R.id.nav_host_fragment) }
        val appBarConfiguration =
            navController?.graph?.let { AppBarConfiguration.Builder(it).build() }
        if (navController != null) {
            if (appBarConfiguration != null) {
                NavigationUI.setupWithNavController(layout, toolbar, navController, appBarConfiguration)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
}