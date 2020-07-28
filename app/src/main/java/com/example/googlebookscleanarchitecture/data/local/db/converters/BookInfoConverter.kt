package com.example.googlebookscleanarchitecture.data.local.db.converters

import androidx.room.TypeConverter
import com.example.googlebookscleanarchitecture.data.model.api.BookInfo
import com.google.gson.Gson

class BookInfoConverter {
    @TypeConverter
    fun bookInfoToJsonString(bookInfo: BookInfo) : String = Gson().toJson(bookInfo)

    @TypeConverter
    fun jsonStringToBookInfo(string : String) : BookInfo = Gson().fromJson(string , BookInfo::class.java)
}