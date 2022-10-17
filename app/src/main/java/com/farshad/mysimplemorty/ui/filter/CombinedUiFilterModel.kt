package com.farshad.mysimplemorty.ui.filter

data class CombinedUiFilterModel(
    val filterByStatus : List<UiFilter> ,
    val filterByGender : List<UiFilter>
)
