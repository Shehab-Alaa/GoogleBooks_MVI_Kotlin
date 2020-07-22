package com.example.googlebookscleanarchitecture.di.module

import android.app.Application
import android.content.Context
import com.example.googlebookscleanarchitecture.data.remote.ApiClient
import com.example.googlebookscleanarchitecture.data.remote.ApiService
import com.example.googlebookscleanarchitecture.intent.BookIntent
import com.example.googlebookscleanarchitecture.utils.NetworkUtils
import com.example.googlebookscleanarchitecture.view.main.book.BooksAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun provideApiService(retrofit : Retrofit) : ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(ApiClient.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideCache(context: Context): Cache {
        val cacheSize : Long = 10 * 1024 * 1024
        return Cache(context.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            request = if (!NetworkUtils.isNetworkAvailable(context)) {
                val maxStale = 60 * 60 * 24 * 28 // 4-weeks-stale
                request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale$maxStale")
                    .build()
            } else {
                val maxAge = 5 // fresh data
                request.newBuilder()
                    .header("Cache-Control", "public, max-age = $maxAge")
                    .build()
            }
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideAppContext(application: Application): Context = application

    @Provides
    fun provideBookIntent(apiService: ApiService) : BookIntent = BookIntent(apiService)

    @Provides
    fun provideBooksAdapter() : BooksAdapter = BooksAdapter(mutableListOf())
}
