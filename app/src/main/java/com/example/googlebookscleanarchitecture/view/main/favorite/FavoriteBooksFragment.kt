package com.example.googlebookscleanarchitecture.view.main.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.databinding.FragmentFavoriteBooksBinding
import com.example.googlebookscleanarchitecture.intent.FavoriteBooksIntent
import com.example.googlebookscleanarchitecture.view.base.BaseFragment
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
        favoriteBooksIntent.bind(this)
    }

    private fun initRecyclerView(){
        getViewDataBinding().favoriteBooksRv.layoutManager = LinearLayoutManager(context)
        getViewDataBinding().favoriteBooksRv.setHasFixedSize(true)
        getViewDataBinding().favoriteBooksRv.adapter = favoriteBooksAdapter
    }

    override fun render(booksState: BooksState) {
        when(booksState){
            is BooksState.DataState -> renderDataState(booksState)
            is BooksState.LoadingState -> renderLoadingState()
            is BooksState.ErrorState -> renderErrorState(booksState)
        }
    }

    private fun renderDataState(dataState: BooksState.DataState) {
        viewVisibilityControl(emptyLayout = false, shimmerLayout = false, normalLayout = true)
        favoriteBooksAdapter.addItems(dataState.data)
    }

    private fun renderLoadingState() {
        viewVisibilityControl(emptyLayout = false, shimmerLayout = true, normalLayout = false)
    }

    private fun renderErrorState(errorState: BooksState.ErrorState) {
        viewVisibilityControl(emptyLayout = true, shimmerLayout = false, normalLayout = false)
    }

    private fun viewVisibilityControl(emptyLayout : Boolean  , shimmerLayout : Boolean , normalLayout : Boolean){
        if (emptyLayout) getViewDataBinding().favoriteEmptyView.linearLayoutView.visibility = View.VISIBLE else getViewDataBinding().favoriteEmptyView.linearLayoutView.visibility = View.INVISIBLE
        if (shimmerLayout) { getViewDataBinding().favoriteShimmerView.shimmerLayout.visibility = View.VISIBLE
            getViewDataBinding().favoriteShimmerView.shimmerLayout.startShimmer()} else { getViewDataBinding().favoriteShimmerView.shimmerLayout.stopShimmer()
            getViewDataBinding().favoriteShimmerView.shimmerLayout.visibility = View.INVISIBLE}
        if (normalLayout) getViewDataBinding().favoriteBooksRv.visibility = View.VISIBLE else getViewDataBinding().favoriteBooksRv.visibility = View.INVISIBLE
    }

    override val layoutId: Int
        get() = R.layout.fragment_favorite_books

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}