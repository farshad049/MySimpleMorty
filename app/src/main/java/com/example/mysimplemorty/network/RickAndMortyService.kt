package com.example.mysimplemorty.network

import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.example.mysimplemorty.network.responseModel.GetCharactersPageResponse
import com.example.mysimplemorty.network.responseModel.GetEpisodeByIdResponse
import com.example.mysimplemorty.network.responseModel.GetEpisodePageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//queries

interface RickAndMortyService {

    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path("character-id") characterId:Int): Response<GetCharacterByIdResponse>

    @GET("character/")
    suspend fun getCharactersPage(@Query("page") pageIndex:Int): Response<GetCharactersPageResponse>

    @GET("character/")
    suspend fun getCharactersPageByName(@Query("name") characterName:String, @Query("page") pageIndex:Int): Response<GetCharactersPageResponse>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(@Path("episode-id") episodeId:Int):Response<GetEpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getMultipleEpisode(@Path("episode-range")episodeRange:List<String>):Response<List<GetEpisodeByIdResponse>>

    @GET("episode/")
    suspend fun getEpisodePage(@Query("page") pageIndex:Int): Response<GetEpisodePageResponse>

    @GET("character/{character-range}")
    suspend fun getMultipleCharacter(@Path("character-range")characterRange:List<String>):Response<List<GetCharacterByIdResponse>>





}