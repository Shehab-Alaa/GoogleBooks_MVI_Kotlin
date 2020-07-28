package com.example.googlebookscleanarchitecture.data.model.api

import com.example.googlebookscleanarchitecture.data.model.db.Book
import com.google.gson.annotations.SerializedName


data class DataResponse (
    @SerializedName("kind")
    var kind: String ,

    @SerializedName("totalItems")
    var totalItems: Int ,

    @SerializedName("items")
    var books: List<Book>
)