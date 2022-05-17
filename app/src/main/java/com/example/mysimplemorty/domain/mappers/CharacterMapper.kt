package com.example.mysimplemorty.domain.mappers

import com.example.mysimplemorty.domain.models.Character
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import kotlin.system.exitProcess

object CharacterMapper {

    fun buildFrom(response:GetCharacterByIdResponse):Character{
        return Character(
            episodeList = emptyList(),
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(response.location.name,response.location.url),
            name = response.name,
            origin = Character.Origin(response.origin.name,response.origin.url),
            species = response.species,
            status = response.status

        )
    }
}