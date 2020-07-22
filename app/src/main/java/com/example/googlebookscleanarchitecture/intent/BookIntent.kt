package com.example.googlebookscleanarchitecture.intent

import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.data.remote.ApiService
import com.example.googlebookscleanarchitecture.view.main.book.BookView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BookIntent @Inject constructor(private val apiService: ApiService) {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var bookView : BookView

    fun bind(bookView: BookView){
        this.bookView = bookView
        compositeDisposable.add(observeBooksDisplay())
    }

    private fun observeBooksDisplay() = apiService.getBooks()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { bookView.render(BooksState.LoadingState) }
        .subscribe({
            bookView.render(BooksState.DataState(it.books.toMutableList()))
        },{
            bookView.render(BooksState.ErrorState(it.localizedMessage ?: "Response Failed"))
        })
}