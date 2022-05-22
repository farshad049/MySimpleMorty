package com.example.mysimplemorty.network

import com.example.mysimplemorty.domain.models.Character

object NetworkCache {
    //we set key to Int because we take the character by it id which is an integer
    //it will be null after application kills
    val characterMap= mutableMapOf<Int,Character>()
}