package com.example.googlebookscleanarchitecture.view.main.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.intent.BookIntent
import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.databinding.FragmentBooksBinding
import com.example.googlebookscleanarchitecture.view.base.BaseFragment
import com.facebook.shimmer.ShimmerFrameLayout


class BooksFragment : BaseFragment<FragmentBooksBinding>(), BookView {

    private lateinit var booksAdapter: BooksAdapter
    private val bookIntent =  BookIntent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        booksAdapter = BooksAdapter(mutableListOf())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        bookIntent.bind(this)

        getViewDataBinding().emptyView.btnRetry.setOnClickListener {
            onRetryClick()
        }
    }

    private fun initRecyclerView(){
        getViewDataBinding().bookRv.layoutManager = LinearLayoutManager(context)
        getViewDataBinding().bookRv.setHasFixedSize(true)
        getViewDataBinding().bookRv.adapter = booksAdapter
    }

    override fun render(booksState: BooksState) {
        when(booksState){
            is BooksState.DataState -> renderDataState(booksState)
            is BooksState.LoadingState -> renderLoadingState()
            is BooksState.ErrorState -> renderErrorState(booksState)
        }
    }

    override fun onRetryClick() {
        bookIntent.bind(this)
    }

    private fun renderDataState(dataState: BooksState.DataState) {
        viewVisibilityControl(emptyLayout = false, shimmerLayout = false, normalLayout = true)
        booksAdapter.addItems(dataState.data)
    }

    private fun renderLoadingState() {
        viewVisibilityControl(emptyLayout = false, shimmerLayout = true, normalLayout = false)
    }

    private fun renderErrorState(errorState: BooksState.ErrorState) {
        viewVisibilityControl(emptyLayout = true, shimmerLayout = false, normalLayout = false)
    }


    private fun viewVisibilityControl(emptyLayout : Boolean  , shimmerLayout : Boolean , normalLayout : Boolean){
        if (emptyLayout) getViewDataBinding().emptyView.linearLayoutView.visibility = View.VISIBLE else getViewDataBinding().emptyView.linearLayoutView.visibility = View.INVISIBLE
        if (shimmerLayout) { getViewDataBinding().shimmerView.shimmerLayout.visibility = View.VISIBLE
            getViewDataBinding().shimmerView.shimmerLayout.startShimmer()} else { getViewDataBinding().shimmerView.shimmerLayout.stopShimmer()
            getViewDataBinding().shimmerView.shimmerLayout.visibility = View.INVISIBLE}
        if (normalLayout) getViewDataBinding().bookRv.visibility = View.VISIBLE else getViewDataBinding().bookRv.visibility = View.INVISIBLE
    }

    override fun onPause() {
        getViewDataBinding().shimmerView.shimmerLayout.stopShimmer()
        getViewDataBinding().shimmerView.shimmerLayout.visibility = View.INVISIBLE
        super.onPause()
    }

    override val layoutId: Int
        get() = R.layout.fragment_books
}