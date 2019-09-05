package com.zack.proxyserver

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        androidApplication().contentResolver.query(
            Uri.parse("content://com.zack.menuprovider.provider/menu"),
            null,
            null,
            null,
            null
        )

    }
    single {
        provideOkHttpClient()
    }

    single {
        provideRetrofit(get())
    }

    single {
        provideApiService(get())
    }

    single {
        MenuRepository(get(), get())
    }

}

fun provideOkHttpClient(): okhttp3.OkHttpClient {
    return OkHttpClient.Builder()
        .build()

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://someurl.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build();

}

fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}






