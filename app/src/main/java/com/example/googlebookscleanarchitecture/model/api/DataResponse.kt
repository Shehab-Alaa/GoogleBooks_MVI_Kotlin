package com.example.googlebookscleanarchitecture.model.api

import com.example.googlebookscleanarchitecture.model.Book
import com.google.gson.annotations.SerializedName


data class DataResponse (
    @SerializedName("kind")
    var kind: String ,

    @SerializedName("totalItems")
    var totalItems: Int ,

    @SerializedName("items")
    var books: List<Book>
)