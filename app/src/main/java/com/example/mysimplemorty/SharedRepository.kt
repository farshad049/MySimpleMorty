package com.example.mysimplemorty

import com.example.mysimplemorty.domain.mappers.CharacterMapper
import com.example.mysimplemorty.domain.models.Character
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.example.mysimplemorty.network.NetworkLayer
import com.example.mysimplemorty.network.responseModel.GetEpisodeByIdResponse

class SharedRepository {
    suspend fun getCharacterById(characterId:Int): Character? {
        val request= NetworkLayer.apiClient.getCharacterById(characterId)
        //if request failed because of network issues
        if (request.failed || !request.isSuccessful) {
            return null
        }
        val networkEpisode = getEpisodesFromCharacterResponse(request.body)
        return CharacterMapper.buildFrom(response = request.body,episodes=networkEpisode)

    }

    private suspend fun getEpisodesFromCharacterResponse(characterResponse:GetCharacterByIdResponse)
    : List<GetEpisodeByIdResponse> {

        //take the episode number from the end of link of each episode
        //we use map to modify changes on a single member of a list and then extend that modo to whole list
        //this return something like this "[1,2,3]"
        val episodeRange=characterResponse.episode.map {
            it.substring(it.lastIndexOf("/")+1)
        }.toString()

        val request=NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }
        return request.body
    }
}