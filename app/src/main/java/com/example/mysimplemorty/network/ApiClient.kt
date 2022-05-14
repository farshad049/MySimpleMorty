package com.example.mysimplemorty.network

import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import retrofit2.Response

class ApiClient(private val rickAndMortyService: RickAndMortyService){

    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(characterId) }
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