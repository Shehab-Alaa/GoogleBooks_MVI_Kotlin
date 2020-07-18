package com.example.googlebookscleanarchitecture.view.main.book

import com.example.googlebookscleanarchitecture.data.model.BooksState

interface BookView {
    fun render(booksState: BooksState)
    fun onRetryClick()
}