package com.example.googlebookscleanarchitecture.intent

import com.example.googlebookscleanarchitecture.data.local.db.AppDatabase
import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.view.main.favorite.FavoriteBooksView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteBooksIntent @Inject constructor(private val appDatabase: AppDatabase) {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var favoriteBooksView: FavoriteBooksView

    fun bind(favoriteBooksView: FavoriteBooksView){
        this.favoriteBooksView = favoriteBooksView
        compositeDisposable.add(observeFavoriteBooksDisplay())
    }

    private fun observeFavoriteBooksDisplay() = appDatabase.booksDAO.loadAll().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { favoriteBooksView.render(BooksState.LoadingState) }
        .subscribe({
            favoriteBooksView.render(BooksState.DataState(it.toMutableList()))
        },{
            favoriteBooksView.render(BooksState.ErrorState(it.localizedMessage ?: "Fetch Failed"))
        })

}