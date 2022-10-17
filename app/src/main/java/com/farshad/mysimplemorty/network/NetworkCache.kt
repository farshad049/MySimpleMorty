package com.farshad.mysimplemorty.network

import com.farshad.mysimplemorty.domain.models.Character

object NetworkCache {
    //we set key to Int because we take the character by it id which is an integer
    //it will be null after application kills
    val characterMap= mutableMapOf<Int,Character>()
}