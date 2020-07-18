package com.example.googlebookscleanarchitecture.view.main.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.data.model.Book
import com.example.googlebookscleanarchitecture.databinding.EmptyItemBookBinding
import com.example.googlebookscleanarchitecture.databinding.ItemBookBinding
import com.example.googlebookscleanarchitecture.view.base.BaseRecyclerViewAdapter
import com.example.googlebookscleanarchitecture.view.base.BaseViewHolder


class BooksAdapter(booksItems : MutableList<Book>) : BaseRecyclerViewAdapter<Book>(booksItems){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BooksViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.item_book , parent , false))
    }

    inner class BooksViewHolder(private val itemBookBinding: ItemBookBinding) : BaseViewHolder(itemBookBinding.root){

        override fun onBind(position: Int) {
            val book = getItems()[position]

            itemBookBinding.bookTitle.text = book.bookInfo?.title
            itemBookBinding.bookAuthor.text = "By " + book.bookInfo?.authors?.get(0)
            itemBookBinding.bookRatingBar.rating = book.bookInfo?.averageRating?.toFloat() ?: 0f
            Glide.with(itemBookBinding.bookThumbnail.context)
                .load(book.bookInfo?.imageLinks?.thumbnail)
                .into(itemBookBinding.bookThumbnail)
            itemBookBinding.bookPageCount.text = book.bookInfo?.pageCount.toString() + " Page"
            val reviews = if (book.bookInfo?.ratingsCount.toString() != "null") book.bookInfo?.ratingsCount.toString() else "0"
            itemBookBinding.bookReviewsCount.text = "$reviews Review"
        }

    }

}