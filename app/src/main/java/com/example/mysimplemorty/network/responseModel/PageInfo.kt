package com.example.mysimplemorty.network.responseModel

data class PageInfo(
    val count: Int = 0,
    val pages: Int = 0,
    val next: String? = null,
    val prev: String? = null
)