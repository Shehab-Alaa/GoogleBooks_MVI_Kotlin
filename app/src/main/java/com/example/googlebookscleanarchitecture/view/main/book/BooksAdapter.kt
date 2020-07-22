package com.example.googlebookscleanarchitecture.view.main.book

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.data.model.Book
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

            // Animation Part
            itemBookBinding.bookThumbnail.animation = AnimationUtils.loadAnimation(itemBookBinding.bookThumbnail.context , R.anim.fade_transition_animation)
            itemBookBinding.imageBackground.animation = AnimationUtils.loadAnimation(itemBookBinding.imageBackground.context , R.anim.fade_scale_animation)
            itemBookBinding.bookTitle.animation = AnimationUtils.loadAnimation( itemBookBinding.bookTitle.context , R.anim.fade_scale_animation)
            itemBookBinding.bookAuthor.animation = AnimationUtils.loadAnimation( itemBookBinding.bookAuthor.context , R.anim.fade_scale_animation)
            itemBookBinding.bookRatingBar.animation = AnimationUtils.loadAnimation(itemBookBinding.bookRatingBar.context , R.anim.fade_scale_animation)
            itemBookBinding.bookPageCount.animation = AnimationUtils.loadAnimation(itemBookBinding.bookPageCount.context , R.anim.fade_scale_animation)
            itemBookBinding.bookReviewsCount.animation = AnimationUtils.loadAnimation(itemBookBinding.bookReviewsCount.context , R.anim.fade_scale_animation)
            itemBookBinding.imageView4.animation = AnimationUtils.loadAnimation(itemBookBinding.imageView4.context , R.anim.fade_scale_animation)

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