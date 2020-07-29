package com.example.googlebookscleanarchitecture.intent

import android.view.View
import com.example.googlebookscleanarchitecture.data.local.db.AppDatabase
import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.example.googlebookscleanarchitecture.view.main.book_details.BookDetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BookDetailsIntent @Inject constructor(private val appDatabase: AppDatabase) {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var bookDetailsView: BookDetailsView
    private lateinit var bookID: String

    fun bind(bookDetailsView: BookDetailsView , bookID : String){
        this.bookDetailsView = bookDetailsView
        this.bookID = bookID
        compositeDisposable.add(observeFavoriteBookState())
    }

    private fun observeFavoriteBookState() = appDatabase.booksDAO.isExist(bookID)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            if (it == 0 )
                bookDetailsView.render(BooksState.UnFavoriteState)
            else
                bookDetailsView.render(BooksState.FavoriteState)
        },{
            bookDetailsView.render(BooksState.UnFavoriteState)
        })


    fun onFavoriteFabClick(book : Book, isFavorite : Boolean){
        if (isFavorite)
            deleteFavoriteBook()
        else
            addFavoriteBook(book)
    }

    private fun addFavoriteBook(book: Book){
        appDatabase.booksDAO.insert(book)
        bookDetailsView.render(BooksState.FavoriteState)
    }

    private fun deleteFavoriteBook(){
        appDatabase.booksDAO.delete(bookID)
        bookDetailsView.render(BooksState.UnFavoriteState)
    }
}