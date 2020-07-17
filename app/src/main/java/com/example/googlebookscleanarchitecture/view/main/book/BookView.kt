package com.example.googlebookscleanarchitecture.view.main.book

import com.example.googlebookscleanarchitecture.model.BooksState

interface BookView {
    fun render(booksState: BooksState)
    fun onRetryClick()
}