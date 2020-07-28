package com.example.googlebookscleanarchitecture.view.main.favorite

import com.example.googlebookscleanarchitecture.data.model.BooksState

interface FavoriteBooksView {
    fun render(booksState: BooksState)
}