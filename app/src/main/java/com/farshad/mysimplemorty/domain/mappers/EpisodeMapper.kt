package com.farshad.mysimplemorty.domain.mappers

import com.farshad.mysimplemorty.domain.models.Episode
import com.farshad.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.farshad.mysimplemorty.network.responseModel.GetEpisodeByIdResponse

object EpisodeMapper {

    fun buildFrom(
        networkEpisode: GetEpisodeByIdResponse,
        networkCharacters: List<GetCharacterByIdResponse> = emptyList()
    ): Episode {
        return Episode(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.air_date,
            episode = networkEpisode.episode,
            seasonNumber = getSeasonNumberFrom(networkEpisode.episode),
            episodeNumber = getEpisodeFrom(networkEpisode.episode),
            character =networkCharacters.map {
                CharacterMapper.buildFrom(it)
            }
        )
    }

    //make this two function to separate S01E02 to 1 and 2
    private fun getSeasonNumberFrom(episode:String):Int{
        val endIndex=episode.indexOfFirst { it=='e' }
        if (endIndex == -1) return 0
        return episode.substring(1,endIndex).toIntOrNull() ?: 0
    }

    private fun getEpisodeFrom(episode:String):Int{
        val firstIndex=episode.indexOfFirst { it=='e' }
        if (firstIndex == -1) return 0
        return episode.substring(firstIndex+1).toIntOrNull() ?:0


    }
}