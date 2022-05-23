package com.example.mysimplemorty.network

import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.example.mysimplemorty.network.responseModel.GetCharactersPageResponse
import com.example.mysimplemorty.network.responseModel.GetEpisodeByIdResponse
import com.example.mysimplemorty.network.responseModel.GetEpisodePageResponse
import retrofit2.Response

//in order to add a extra layer for checking network issues

class ApiClient(private val rickAndMortyService: RickAndMortyService){

    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(characterId) }
    }

    suspend fun getCharactersPage(pageIndex:Int): SimpleResponse<GetCharactersPageResponse> {
        //set access to rickAndMortyService in here in order to take care of network issues
        return safeApiCall { rickAndMortyService.getCharactersPage(pageIndex) }
    }

    suspend fun getEpisodeById(episodeId:Int):SimpleResponse<GetEpisodeByIdResponse>{
        return safeApiCall { rickAndMortyService.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodeRange(episodeRange:String):SimpleResponse<List<GetEpisodeByIdResponse>>{
        return safeApiCall { rickAndMortyService.getEpisodeRange(episodeRange) }
    }

    suspend fun getEpisodePage(pageIndex:Int):SimpleResponse<GetEpisodePageResponse>{
        return safeApiCall { rickAndMortyService.getEpisodePage(pageIndex) }
    }




    //run safe check for network issues
    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}