package com.example.googlebookscleanarchitecture.view.main.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.model.Book
import com.example.googlebookscleanarchitecture.view.base.BaseRecyclerViewAdapter
import com.example.googlebookscleanarchitecture.view.base.BaseViewHolder
import org.w3c.dom.Text


class BooksAdapter(booksItems : MutableList<Book>) : BaseRecyclerViewAdapter<Book>(booksItems){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BooksViewHolder(view)
    }

    inner class BooksViewHolder(itemView : View) : BaseViewHolder(itemView){

        override fun onBind(position: Int) {
            val book = getItems()[position]

            val bookTitleTxt : TextView = itemView.findViewById(R.id.book_title)
            val bookAuthor : TextView = itemView.findViewById(R.id.book_author)
            val bookThumbnailImage : ImageView = itemView.findViewById(R.id.book_thumbnail)
            val bookRatingBar : RatingBar = itemView.findViewById(R.id.book_rating_bar)
            val bookPagesCount : TextView = itemView.findViewById(R.id.book_page_count)
            val bookReviewsCount : TextView = itemView.findViewById(R.id.book_reviews_count)

            bookTitleTxt.text = book.bookInfo?.title
            bookAuthor.text = "By " + book.bookInfo?.authors?.get(0)
            bookRatingBar.rating = book.bookInfo?.averageRating?.toFloat() ?: 0f
            Glide.with(bookThumbnailImage.context)
                .load(book.bookInfo?.imageLinks?.thumbnail)
                .into(bookThumbnailImage )
            bookPagesCount.text = book.bookInfo?.pageCount.toString() + " Page"
            val reviews = if (book.bookInfo?.ratingsCount.toString() != "null") book.bookInfo?.ratingsCount.toString() else "0"
            bookReviewsCount.text = "$reviews Review"

        }

    }

}