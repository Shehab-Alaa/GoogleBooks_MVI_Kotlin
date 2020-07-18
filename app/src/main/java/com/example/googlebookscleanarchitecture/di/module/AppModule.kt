package com.example.googlebookscleanarchitecture.di.module

import android.app.Application
import android.content.Context
import com.example.googlebookscleanarchitecture.data.remote.ApiClient
import com.example.googlebookscleanarchitecture.data.remote.ApiService
import com.example.googlebookscleanarchitecture.utils.NetworkUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { get<Retrofit>().create(ApiService::class.java) }
    single { provideRetrofit(get()) }
    single { provideOkHttpClient(get() , get ()) }
    single { provideInterceptor(get()) }
    single { provideCache(get()) }
}

private fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
    .baseUrl(ApiClient.BASE_URL)
    .client(okHttpClient)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()


private fun provideCache(context: Context): Cache {
    val cacheSize : Long = 10 * 1024 * 1024
    return Cache(context.cacheDir, cacheSize)
}

private fun provideOkHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient {
    return OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(interceptor)
        .build()
}

private fun provideInterceptor(context: Context): Interceptor {
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

private fun provideAppContext(application: Application) = application
