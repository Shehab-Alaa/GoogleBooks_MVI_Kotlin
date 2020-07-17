package com.example.googlebookscleanarchitecture.model.api

import com.google.gson.annotations.SerializedName

class ImageLinks {
    @SerializedName("smallThumbnail")
    var smallThumbnail: String? = null

    @SerializedName("thumbnail")
    var thumbnail: String? = null
}