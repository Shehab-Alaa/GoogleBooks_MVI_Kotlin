package com.example.googlebookscleanarchitecture.data.remote

import com.example.googlebookscleanarchitecture.data.model.api.DataResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET(ApiEndPoint.END_POINT_GOOGLE_BOOKS + ApiClient.SPORT_CATEGORY + ApiClient.MAX_BOOKS)
    fun getBooks() : Single<DataResponse>
}