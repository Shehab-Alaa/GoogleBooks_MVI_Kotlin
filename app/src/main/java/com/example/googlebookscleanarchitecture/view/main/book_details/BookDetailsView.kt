package com.example.googlebookscleanarchitecture.view.main.book_details

import com.example.googlebookscleanarchitecture.data.model.BooksState

interface BookDetailsView {
    fun render(booksState: BooksState)
}