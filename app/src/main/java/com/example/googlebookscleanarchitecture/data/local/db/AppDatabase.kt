package com.example.googlebookscleanarchitecture.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.googlebookscleanarchitecture.data.local.db.converters.BookInfoConverter
import com.example.googlebookscleanarchitecture.data.local.db.converters.ImageLinksConverter
import com.example.googlebookscleanarchitecture.data.local.db.dao.BooksDAO
import com.example.googlebookscleanarchitecture.data.model.db.Book

@Database(entities = [Book::class] , version = 1, exportSchema = false)
@TypeConverters(BookInfoConverter::class , ImageLinksConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val booksDAO : BooksDAO
}