package com.example.mysimplemorty.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.mysimplemorty.SimpleMortyApplication
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//make connection with API
object NetworkLayer {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val retrofit: Retrofit = Retrofit.Builder()
        .client(getLoggingHttpClient())
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val rickAndMortyService : RickAndMortyService by
    lazy{ retrofit.create(RickAndMortyService::class.java) }

    val apiClient= ApiClient(rickAndMortyService)

    //region take care of network logging
    private fun getLoggingHttpClient():OkHttpClient{
        val builder=OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        })
        //region make an external application so we can see logging on device
        builder.addInterceptor(
            ChuckerInterceptor.Builder(SimpleMortyApplication.context)
                .collector(ChuckerCollector(SimpleMortyApplication.context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
        //endregion
        return builder.build()
    }
    //endregion take care of network logging
}