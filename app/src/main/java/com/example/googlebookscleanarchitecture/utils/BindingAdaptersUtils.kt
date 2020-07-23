package com.example.googlebookscleanarchitecture.utils

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdaptersUtils {

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    @BindingAdapter("android:bookAuthor")
    fun setBookAuthorText(authorTextView : TextView , authorName : String) {
        if (authorName != "null"){
            val textHolder = "By $authorName"
            authorTextView.text = textHolder
        }
    }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    @BindingAdapter("android:bookRate")
    fun setBookRatingBar(bookRatingBar : RatingBar, bookRate : Double) {
        bookRatingBar.rating = bookRate.toFloat()
    }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    @BindingAdapter("android:bookThumbnail")
    fun setBookCover(bookThumbnail : ImageView, thumbnailLink : String) {
        Glide.with(bookThumbnail.context)
            .load(thumbnailLink)
            .into(bookThumbnail)
    }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    @BindingAdapter("android:bookPageCount")
    fun setBookPageCount(bookPageCount : TextView, pageCount : Int) {
        val textHolder = "$pageCount Pages"
        bookPageCount.text = textHolder
    }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    @BindingAdapter("android:bookReviews")
    fun setBookReviews(bookReviewsCount : TextView, bookReviews : Int) {
        val textHolder = if (bookReviews.toString() != "null") "$bookReviews Reviews" else "0 Reviews"
        bookReviewsCount.text = textHolder
    }

}