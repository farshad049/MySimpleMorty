package com.example.mysimplemorty

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path("character-id") CharacterId:Int): Response<GetCharacterByIDResponse>

}