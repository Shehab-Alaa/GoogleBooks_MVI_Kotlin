package com.example.googlebookscleanarchitecture.intent

import android.util.Log
import com.example.googlebookscleanarchitecture.model.BooksState
import com.example.googlebookscleanarchitecture.model.remote.ApiService
import com.example.googlebookscleanarchitecture.view.main.book.BookView
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class BookIntent  : KoinComponent{

    private val apiService : ApiService by inject()
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