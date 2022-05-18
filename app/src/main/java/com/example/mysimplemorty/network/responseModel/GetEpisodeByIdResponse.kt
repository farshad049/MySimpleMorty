package com.example.mysimplemorty.network.responseModel

data class GetEpisodeByIdResponse(
    val air_date: String = "",
    val characters: List<Any> = listOf(),
    val created: String = "",
    val episode: String = "",
    val id: Int = 0,
    val name: String = "",
    val url: String = ""
)