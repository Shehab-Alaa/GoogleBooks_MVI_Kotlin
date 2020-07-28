package com.example.googlebookscleanarchitecture.data.model.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.googlebookscleanarchitecture.data.model.api.BookInfo
import com.example.googlebookscleanarchitecture.utils.AppConstants
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity (tableName = AppConstants.DATABASE_NAME)
class Book : Serializable {
    @SerializedName("kind")
    var kind: String? = null

    @SerializedName("id")
    @PrimaryKey
    @NonNull
    var id: String? = null

    @SerializedName("etag")
    var etag: String? = null

    @SerializedName("selfLink")
    var selfLink: String? = null

    @SerializedName("volumeInfo")
    var bookInfo: BookInfo? = null
}