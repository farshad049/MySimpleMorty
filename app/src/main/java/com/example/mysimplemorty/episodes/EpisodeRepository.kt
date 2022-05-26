package com.example.mysimplemorty.episodes

import com.example.mysimplemorty.domain.mappers.EpisodeMapper
import com.example.mysimplemorty.domain.models.Episode
import com.example.mysimplemorty.network.NetworkLayer
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.example.mysimplemorty.network.responseModel.GetEpisodeByIdResponse
import com.example.mysimplemorty.network.responseModel.GetEpisodePageResponse

class EpisodeRepository {
    suspend fun fetchEpisodePage(pageIndex:Int):GetEpisodePageResponse?{
        val request=NetworkLayer.apiClient.getEpisodePage(pageIndex)
        if (!request.isSuccessful){
            return null
        }
        return request.body
    }

    suspend fun getEpisodeById(episodeId: Int): Episode? {
        val request = NetworkLayer.apiClient.getEpisodeById(episodeId)
        if (!request.isSuccessful) {
            return null
        }
        val networkCharacter = getCharactersFromEpisodeResponse(request.body)
        return EpisodeMapper.buildFrom(
            networkEpisode = request.body,
            networkCharacters = networkCharacter
        )
    }

    private suspend fun getCharactersFromEpisodeResponse(
        episodeByIdResponse: GetEpisodeByIdResponse
    ): List<GetCharacterByIdResponse> {
        val characterList = episodeByIdResponse.characters.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }

        val request = NetworkLayer.apiClient.getMultipleCharacter(characterList)
        return request.bodyNullable ?: emptyList()
    }
}


