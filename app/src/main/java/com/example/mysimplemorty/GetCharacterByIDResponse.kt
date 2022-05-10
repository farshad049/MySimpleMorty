package com.example.mysimplemorty

import com.squareup.moshi.Json

data class GetCharacterByIDResponse(
    //use this to name the field with another name
    //@Json(name="full_name")
    val created: String = "",
    val episode: List<String> = listOf(),
    val gender: String = "",
    val id: Int = 0,
    val image: String = "",
    val location: Location = Location(),
    val name: String = "",
    val origin: Origin = Origin(),
    val species: String = "",
    val status: String = "",
    val type: String = "",
    val url: String = ""
) {
    data class Location(
        val name: String = "",
        val url: String = ""
    )

    data class Origin(
        val name: String = "",
        val url: String = ""
    )
}