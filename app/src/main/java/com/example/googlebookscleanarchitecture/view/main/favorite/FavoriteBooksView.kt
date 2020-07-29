package com.example.googlebookscleanarchitecture.view.main.favorite

import android.view.View
import com.example.googlebookscleanarchitecture.data.model.BooksState
import com.example.googlebookscleanarchitecture.data.model.db.Book

interface FavoriteBooksView {
    fun render(booksState: BooksState)
    fun onBookItemClick(view : View, book : Book)
}