package com.example.googlebookscleanarchitecture.data.local.db.converters

import androidx.room.TypeConverter
import com.example.googlebookscleanarchitecture.data.model.api.ImageLinks
import com.google.gson.Gson

class ImageLinksConverter {
    @TypeConverter
    fun imageLinkToJsonString(imageLinks: ImageLinks) : String = Gson().toJson(imageLinks)

    @TypeConverter
    fun jsonStringToImageLink(string : String) : ImageLinks = Gson().fromJson(string , ImageLinks::class.java)
}