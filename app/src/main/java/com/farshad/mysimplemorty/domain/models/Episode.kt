package com.farshad.mysimplemorty.domain.models

data class Episode(
    val id:Int=0,
    val name:String="",
    val airDate:String="",
    val episode:String="",
    val character: List<Character> = listOf(),
    val seasonNumber:Int=0,
    val episodeNumber:Int=0,
)