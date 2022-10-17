package com.farshad.mysimplemorty.ui.filter

data class FilterByGenderModel(
    val species: Set<String> = setOf(),
    var selectedSpecies: Set<String> = emptySet()
)
