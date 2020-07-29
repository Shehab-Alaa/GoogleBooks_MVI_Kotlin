package com.example.googlebookscleanarchitecture.view.main.favorite

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.example.googlebookscleanarchitecture.databinding.FragmentFavoriteBooksBinding
import com.example.googlebookscleanarchitecture.intent.FavoriteBooksIntent
import com.example.googlebookscleanarchitecture.utils.ViewUtils
import com.example.googlebookscleanarchitecture.view.base.BaseFragment
import com.example.googlebookscleanarchitecture.view.main.book.BooksFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.empty_item_book.view.*
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteBooksFragment : BaseFragment<FragmentFavoriteBooksBinding>() , FavoriteBooksView {

    @Inject
    lateinit var favoriteBooksAdapter : FavoriteBooksAdapter
    @Inject
    lateinit var favoriteBooksIntent: FavoriteBooksIntent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewControllers()
        favoriteBooksIntent.bind(this)
    }

    private fun initRecyclerView(){
        getViewDataBinding().favoriteBooksRv.layoutManager = LinearLayoutManager(context)
        getViewDataBinding().favoriteBooksRv.setHasFixedSize(true)
        getViewDataBinding().favoriteBooksRv.adapter = favoriteBooksAdapter
    }

    private fun initViewControllers(){
        ViewUtils.emptyLayoutView = getViewDataBinding().favoriteEmptyView.linearLayoutView
        ViewUtils.shimmerLayoutView = getViewDataBinding().favoriteShimmerView.shimmerLayout
        ViewUtils.recyclerView = getViewDataBinding().favoriteBooksRv
    }

    override fun render(booksState: BooksState) {
        when(booksState){
            is BooksState.DataState -> renderDataState(booksState)
            is BooksState.LoadingState -> renderLoadingState()
            is BooksState.ErrorState -> renderErrorState(booksState)
        }
    }

    private fun renderDataState(dataState: BooksState.DataState) {
        ViewUtils.viewVisibilityControl(emptyLayout = false, shimmerLayout = false, normalLayout = true)
        favoriteBooksAdapter.addItems(dataState.data)
    }

    private fun renderLoadingState() {
        ViewUtils.viewVisibilityControl(emptyLayout = false, shimmerLayout = true, normalLayout = false)
    }

    private fun renderErrorState(errorState: BooksState.ErrorState) {
        ViewUtils.viewVisibilityControl(emptyLayout = true, shimmerLayout = false, normalLayout = false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBookItemClick(view: View, book: Book) {
        view.transitionName = book.bookInfo?.imageLinks?.thumbnail
        val extras = FragmentNavigatorExtras(view to book.bookInfo?.imageLinks?.thumbnail.toString())
        val action = FavoriteBooksFragmentDirections.actionFavoriteBooksFragmentToBookDetailsFragment(book)
        getNavController().navigate(action,extras)
    }

    override val layoutId: Int
        get() = R.layout.fragment_favorite_books

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}