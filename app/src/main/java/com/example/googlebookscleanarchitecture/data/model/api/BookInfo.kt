package com.example.googlebookscleanarchitecture.data.model.api

import com.google.gson.annotations.SerializedName

class BookInfo {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("authors")
    var authors: List<String>? = null

    @SerializedName("publisher")
    var publisher: String? = null

    @SerializedName("publishedDate")
    var publishedDate: String? = null

    @SerializedName("description")
    var description: String? = null


    @SerializedName("pageCount")
    var pageCount: Int? = null

    @SerializedName("printType")
    var printType: String? = null

    @SerializedName("categories")
    var categories: List<String>? = null

    @SerializedName("averageRating")
    var averageRating: Double? = null

    @SerializedName("ratingsCount")
    var ratingsCount: Int? = null

    @SerializedName("maturityRating")
    var maturityRating: String? = null

    @SerializedName("allowAnonLogging")
    var allowAnonLogging: Boolean? = null

    @SerializedName("contentVersion")
    var contentVersion: String? = null

    @SerializedName("imageLinks")
    var imageLinks: ImageLinks? = null

    @SerializedName("language")
    var language: String? = null

    @SerializedName("previewLink")
    var previewLink: String? = null

    @SerializedName("infoLink")
    var infoLink: String? = null

    @SerializedName("canonicalVolumeLink")
    var canonicalVolumeLink: String? = null
}