package com.example.googlebookscleanarchitecture.data.model

import com.example.googlebookscleanarchitecture.data.model.db.Book

sealed class BooksState {
    object LoadingState : BooksState()
    data class DataState(val data: MutableList<Book>) : BooksState()
    data class ErrorState(val data: String) : BooksState()
    object FavoriteState : BooksState()
    object UnFavoriteState : BooksState()
}