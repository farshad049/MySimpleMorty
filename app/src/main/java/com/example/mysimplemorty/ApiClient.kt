package com.example.mysimplemorty

import retrofit2.Response

class ApiClient(private val rickAndMortyService: RickAndMortyService){

    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIDResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(characterId) }
    }


    //run safe check
    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}