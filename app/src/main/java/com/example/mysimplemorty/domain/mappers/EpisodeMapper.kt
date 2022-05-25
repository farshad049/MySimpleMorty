package com.example.mysimplemorty.domain.mappers

import com.example.mysimplemorty.domain.models.Episode
import com.example.mysimplemorty.network.responseModel.GetEpisodeByIdResponse

object EpisodeMapper {

    fun buildFrom(response: GetEpisodeByIdResponse):Episode{
        return Episode(
            id = response.id,
            name = response.name,
            airDate = response.air_date,
            episode = response.episode,
            seasonNumber = getSeasonNumberFrom(response.episode),
            episodeNumber = getEpisodeFrom(response.episode)
        )
    }
    //make this two function to separate S01E02 to 1 and 2
    private fun getSeasonNumberFrom(episode:String):Int{
        val endIndex=episode.indexOfFirst { it.equals('e',true) }
        if (endIndex == -1) return 0
        return episode.substring(1,endIndex).toIntOrNull() ?: 0
    }

    private fun getEpisodeFrom(episode:String):Int{
        val firstIndex=episode.indexOfFirst { it.equals('e',true) }
        if (firstIndex == -1) return 0
        return episode.substring(firstIndex+1).toIntOrNull() ?:0


    }
}