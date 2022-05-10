package com.example.mysimplemorty

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val rickAndMortyService : RickAndMortyService=retrofit.create(RickAndMortyService::class.java)

        //handling on response and on failure for retrofit service
        rickAndMortyService.getCharacterById().enqueue(object :Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.i("MainActivity",response.toString())
            }
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i("MainActivity", t.message ?:"null message")
            }
        })



    }//FUN




}