package com.example.googlebookscleanarchitecture.data.model.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageLinks : Serializable{
    @SerializedName("smallThumbnail")
    var smallThumbnail: String? = null

    @SerializedName("thumbnail")
    var thumbnail: String? = null
}