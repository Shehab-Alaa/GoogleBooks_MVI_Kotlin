package com.example.googlebookscleanarchitecture.data.model.api

import com.google.gson.annotations.SerializedName

class ImageLinks {
    @SerializedName("smallThumbnail")
    var smallThumbnail: String? = null

    @SerializedName("thumbnail")
    var thumbnail: String? = null
}