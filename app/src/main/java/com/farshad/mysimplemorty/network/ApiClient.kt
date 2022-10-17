package com.farshad.mysimplemorty.network

import com.farshad.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.farshad.mysimplemorty.network.responseModel.GetCharactersPageResponse
import com.farshad.mysimplemorty.network.responseModel.GetEpisodeByIdResponse
import com.farshad.mysimplemorty.network.responseModel.GetEpisodePageResponse
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

    suspend fun getCharactersPageByName(characterName:String ,pageIndex:Int ): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { rickAndMortyService.getCharactersPageByName(characterName,pageIndex) }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse>{
        return safeApiCall { rickAndMortyService.getEpisodeById(episodeId) }
    }

    suspend fun getMultipleEpisode(episodeRange:List<String>):SimpleResponse<List<GetEpisodeByIdResponse>>{
        return safeApiCall { rickAndMortyService.getMultipleEpisode(episodeRange) }
    }

    suspend fun getEpisodePage(pageIndex:Int):SimpleResponse<GetEpisodePageResponse>{
        return safeApiCall { rickAndMortyService.getEpisodePage(pageIndex) }
    }

    suspend fun getMultipleCharacter(characterRange: List<String>): SimpleResponse<List<GetCharacterByIdResponse>>{
        return safeApiCall { rickAndMortyService.getMultipleCharacter(characterRange) }
    }

    suspend fun getCharactersByParameters(
        status:String ,
        species : String ,
        pageIndex:Int
    ): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { rickAndMortyService.getCharactersByParameters(status,species,pageIndex) }
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