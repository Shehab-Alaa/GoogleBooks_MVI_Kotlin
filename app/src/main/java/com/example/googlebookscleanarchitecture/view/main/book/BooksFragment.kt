package com.example.googlebookscleanarchitecture.view.main.book

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.databinding.FragmentBooksBinding
import com.example.googlebookscleanarchitecture.intent.BooksIntent
import com.example.googlebookscleanarchitecture.utils.ViewUtils
import com.example.googlebookscleanarchitecture.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BooksFragment : BaseFragment<FragmentBooksBinding>(), BooksView {

    @Inject lateinit var booksAdapter: BooksAdapter
    @Inject lateinit var booksIntent : BooksIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return getMRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initRecyclerView()
        initViewControllers()
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

    private fun initViewControllers(){
        ViewUtils.emptyLayoutView = getViewDataBinding().emptyView.linearLayoutView
        ViewUtils.shimmerLayoutView = getViewDataBinding().shimmerView.shimmerLayout
        ViewUtils.recyclerView = getViewDataBinding().bookRv
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
        ViewUtils.viewVisibilityControl(emptyLayout = false, shimmerLayout = false, normalLayout = true)
        booksAdapter.addItems(dataState.data)
    }

    private fun renderLoadingState() {
        ViewUtils.viewVisibilityControl(emptyLayout = false, shimmerLayout = true, normalLayout = false)
    }

    private fun renderErrorState(errorState: BooksState.ErrorState) {
        ViewUtils.viewVisibilityControl(emptyLayout = true, shimmerLayout = false, normalLayout = false)
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