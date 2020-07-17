package com.example.googlebookscleanarchitecture.view.main.book

import android.os.Bundle
import android.util.Log
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
import com.example.googlebookscleanarchitecture.model.BooksState
import com.example.googlebookscleanarchitecture.model.remote.ApiClient
import com.example.googlebookscleanarchitecture.model.remote.ApiService
import com.facebook.shimmer.ShimmerFrameLayout
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject


class BooksFragment : Fragment() , BookView {

    private lateinit var retryBtn : Button
    private lateinit var emptyLayout : LinearLayout
    private lateinit var shimmerLayout : ShimmerFrameLayout
    private lateinit var bookRv : RecyclerView
    private lateinit var booksAdapter: BooksAdapter
    private val bookIntent =  BookIntent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        booksAdapter = BooksAdapter(mutableListOf())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // TODO("rxBinding")
       // TODO("MVI Better Example Clean Code")
       // TODO("Book Details")
       // TODO("Constrains Text View")

        retryBtn = view.findViewById(R.id.btn_retry)
        emptyLayout = view.findViewById(R.id.empty_view)
        shimmerLayout = view.findViewById(R.id.shimmer_view)
        bookRv  = view.findViewById(R.id.book_rv)

        initRecyclerView()
        bookIntent.bind(this)

        retryBtn.setOnClickListener {
            onRetryClick()
        }
    }

    private fun initRecyclerView(){
        bookRv.layoutManager = LinearLayoutManager(context)
        bookRv.setHasFixedSize(true)
        bookRv.adapter = booksAdapter
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
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.INVISIBLE
        emptyLayout.visibility = View.INVISIBLE
        bookRv.visibility = View.VISIBLE
        booksAdapter.addItems(dataState.data)
    }

    private fun renderLoadingState() {
        emptyLayout.visibility = View.INVISIBLE
        shimmerLayout.visibility = View.VISIBLE
        shimmerLayout.startShimmer()
    }

    private fun renderErrorState(errorState: BooksState.ErrorState) {
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.INVISIBLE
        bookRv.visibility = View.INVISIBLE
        emptyLayout.visibility = View.VISIBLE
    }


    override fun onPause() {
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.INVISIBLE
        super.onPause()
    }
}