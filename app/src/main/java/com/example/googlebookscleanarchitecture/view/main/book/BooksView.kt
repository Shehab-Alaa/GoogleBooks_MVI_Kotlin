package com.example.googlebookscleanarchitecture.view.main.book

import android.view.View
import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.example.googlebookscleanarchitecture.data.model.BooksState

interface BooksView {
    fun render(booksState: BooksState)
    fun onBookItemClick(view : View, book : Book)
}