package com.example.googlebookscleanarchitecture.model

sealed class BooksState {
    object LoadingState : BooksState()
    data class DataState(val data: MutableList<Book>) : BooksState()
    data class ErrorState(val data: String) : BooksState()
}