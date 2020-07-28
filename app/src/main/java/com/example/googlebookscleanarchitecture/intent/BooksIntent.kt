package com.example.googlebookscleanarchitecture.intent

import android.view.View
import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.data.remote.ApiService
import com.example.googlebookscleanarchitecture.view.main.book.BooksView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BooksIntent @Inject constructor(private val apiService: ApiService) {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var booksView : BooksView

    fun bind(booksView: BooksView){
        this.booksView = booksView
        compositeDisposable.add(observeBooksDisplay())
    }

    fun onRetryClickEvent(){
        compositeDisposable.clear()
        compositeDisposable.add(observeBooksDisplay())
    }

    private fun observeBooksDisplay() = apiService.getBooks()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { booksView.render(BooksState.LoadingState) }
        .subscribe({
            booksView.render(BooksState.DataState(it.books.toMutableList()))
        },{
            booksView.render(BooksState.ErrorState(it.localizedMessage ?: "Response Failed"))
        })

    fun onBookItemClick(view : View, book : Book){
        booksView.onBookItemClick(view,book)
    }
}