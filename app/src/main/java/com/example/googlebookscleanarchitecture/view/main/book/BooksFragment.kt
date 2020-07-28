package com.example.googlebookscleanarchitecture.view.main.book

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.databinding.FragmentBooksBinding
import com.example.googlebookscleanarchitecture.intent.BooksIntent
import com.example.googlebookscleanarchitecture.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BooksFragment : BaseFragment<FragmentBooksBinding>(), BooksView {

    @Inject lateinit var booksAdapter: BooksAdapter
    @Inject lateinit var booksIntent : BooksIntent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initRecyclerView()
        booksIntent.bind(this)

        getViewDataBinding().emptyView.btnRetry.setOnClickListener {
            booksIntent.onRetryClickEvent()
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_books

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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBookItemClick(view: View, book: Book) {
        view.transitionName = book.bookInfo?.imageLinks?.thumbnail
        val extras = FragmentNavigatorExtras(view to book.bookInfo?.imageLinks?.thumbnail.toString())
        val action = BooksFragmentDirections.actionBooksFragmentToBookDetailsFragment(book)
        getNavController().navigate(action,extras)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main , menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_booksFragment_to_favoriteBooksFragment)
        {
            val action = BooksFragmentDirections.actionBooksFragmentToFavoriteBooksFragment()
            getNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onPause() {
        getViewDataBinding().shimmerView.shimmerLayout.stopShimmer()
        getViewDataBinding().shimmerView.shimmerLayout.visibility = View.INVISIBLE
        super.onPause()
    }

}