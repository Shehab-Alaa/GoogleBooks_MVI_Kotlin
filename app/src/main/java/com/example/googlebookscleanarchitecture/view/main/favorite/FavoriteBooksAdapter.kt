package com.example.googlebookscleanarchitecture.view.main.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.googlebookscleanarchitecture.R
import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.example.googlebookscleanarchitecture.databinding.ItemFavoriteBookBinding
import com.example.googlebookscleanarchitecture.intent.FavoriteBooksIntent
import com.example.googlebookscleanarchitecture.view.base.BaseRecyclerViewAdapter
import com.example.googlebookscleanarchitecture.view.base.BaseViewHolder

class FavoriteBooksAdapter(private val favoriteBooksIntent: FavoriteBooksIntent , booksItems : MutableList<Book>) : BaseRecyclerViewAdapter<Book>(booksItems){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BooksViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.item_favorite_book , parent , false))
    }

    inner class BooksViewHolder(private val itemFavoriteBookBinding: ItemFavoriteBookBinding) : BaseViewHolder(itemFavoriteBookBinding.root){

        override fun onBind(position: Int) {
            // Animation Part
            itemFavoriteBookBinding.bookThumbnail.animation = AnimationUtils.loadAnimation(itemFavoriteBookBinding.bookThumbnail.context , R.anim.fade_transition_animation)
            itemFavoriteBookBinding.imageBackground.animation = AnimationUtils.loadAnimation(itemFavoriteBookBinding.imageBackground.context , R.anim.fade_scale_animation)
            itemFavoriteBookBinding.bookTitle.animation = AnimationUtils.loadAnimation( itemFavoriteBookBinding.bookTitle.context , R.anim.fade_scale_animation)
            itemFavoriteBookBinding.bookAuthor.animation = AnimationUtils.loadAnimation( itemFavoriteBookBinding.bookAuthor.context , R.anim.fade_scale_animation)
            itemFavoriteBookBinding.bookRatingBar.animation = AnimationUtils.loadAnimation(itemFavoriteBookBinding.bookRatingBar.context , R.anim.fade_scale_animation)
            itemFavoriteBookBinding.bookPageCount.animation = AnimationUtils.loadAnimation(itemFavoriteBookBinding.bookPageCount.context , R.anim.fade_scale_animation)
            itemFavoriteBookBinding.bookReviewsCount.animation = AnimationUtils.loadAnimation(itemFavoriteBookBinding.bookReviewsCount.context , R.anim.fade_scale_animation)
            itemFavoriteBookBinding.imageView4.animation = AnimationUtils.loadAnimation(itemFavoriteBookBinding.imageView4.context , R.anim.fade_scale_animation)
            // Binding Part
            itemFavoriteBookBinding.book = getItems()[position]
            itemFavoriteBookBinding.executePendingBindings()

            itemFavoriteBookBinding.itemLayout.setOnClickListener {
                favoriteBooksIntent.onBookItemClick(itemFavoriteBookBinding.bookThumbnail , getItems()[position])
            }
        }

    }

}