package com.farshad.mysimplemorty.ui.filter

data class FilterByStatusModel(
    val status: Set<String> = setOf(),
    var selectedStatus: Set<String> = emptySet()
)
