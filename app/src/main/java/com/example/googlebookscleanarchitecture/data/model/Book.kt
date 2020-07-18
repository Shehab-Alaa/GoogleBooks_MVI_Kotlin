package com.example.googlebookscleanarchitecture.data.model

import com.example.googlebookscleanarchitecture.data.model.api.BookInfo
import com.google.gson.annotations.SerializedName

class Book {
    @SerializedName("kind")
    var kind: String? = null

    @SerializedName("id")
    var id: String? = null

    @SerializedName("etag")
    var etag: String? = null

    @SerializedName("selfLink")
    var selfLink: String? = null

    @SerializedName("volumeInfo")
    var bookInfo: BookInfo? = null
}