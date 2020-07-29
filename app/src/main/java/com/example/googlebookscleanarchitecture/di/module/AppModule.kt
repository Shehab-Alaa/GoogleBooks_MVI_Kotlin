package com.example.googlebookscleanarchitecture.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.googlebookscleanarchitecture.data.local.db.AppDatabase
import com.example.googlebookscleanarchitecture.data.remote.ApiClient
import com.example.googlebookscleanarchitecture.data.remote.ApiService
import com.example.googlebookscleanarchitecture.intent.BookDetailsIntent
import com.example.googlebookscleanarchitecture.intent.BooksIntent
import com.example.googlebookscleanarchitecture.intent.FavoriteBooksIntent
import com.example.googlebookscleanarchitecture.utils.AppConstants
import com.example.googlebookscleanarchitecture.utils.NetworkUtils
import com.example.googlebookscleanarchitecture.view.main.book.BooksAdapter
import com.example.googlebookscleanarchitecture.view.main.favorite.FavoriteBooksAdapter
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
    @Singleton
    fun provideDatabaseName() : String = AppConstants.DATABASE_NAME

    @Provides
    @Singleton
    fun provideAppDatabase(context : Context , databaseName : String) : AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
        .allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideBookIntent(apiService: ApiService) : BooksIntent = BooksIntent(apiService)

    @Provides
    @Singleton
    fun provideFavoriteBooksIntent(appDatabase: AppDatabase) : FavoriteBooksIntent = FavoriteBooksIntent(appDatabase)

    @Provides
    @Singleton
    fun provideBookDetailsIntent(appDatabase: AppDatabase) : BookDetailsIntent = BookDetailsIntent(appDatabase)

    @Provides
    fun provideBooksAdapter(booksIntent: BooksIntent) : BooksAdapter = BooksAdapter(booksIntent,mutableListOf())

    @Provides
    fun provideFavoriteBooksAdapter(favoriteBooksIntent: FavoriteBooksIntent) : FavoriteBooksAdapter = FavoriteBooksAdapter(favoriteBooksIntent,mutableListOf())

}
