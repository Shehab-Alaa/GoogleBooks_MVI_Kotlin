package com.example.googlebookscleanarchitecture.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.example.googlebookscleanarchitecture.utils.AppConstants
import io.reactivex.Single

@Dao
interface BooksDAO {
    @Insert
    fun insert(book: Book)

    @Query("select * from ${AppConstants.DATABASE_NAME}")
    fun loadAll(): Single<List<Book>>

    @Query("delete from ${AppConstants.DATABASE_NAME} where id = :bookID")
    fun delete(bookID: String)

    @Query("select COUNT(*) from ${AppConstants.DATABASE_NAME} where id = :bookID")
    fun isExist(bookID: String): Single<Int>
}