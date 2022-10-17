package com.farshad.mysimplemorty.ui.filter

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel(){

    val _filterByStatusModelLiveData = MutableStateFlow(
        FilterByStatusModel(
            status = setOf("Alive" , "Dead" , "Unknown"),
            selectedStatus = emptySet()
        )
    )
    val filterByStatusModelLiveData : StateFlow<FilterByStatusModel> = _filterByStatusModelLiveData




    val _filterByGenderModelLiveData = MutableStateFlow(
        FilterByGenderModel(
            species = setOf("Female", "Male", "Genderless" , "Unknown"),
            selectedSpecies = emptySet()
        )
    )
    val filterByGenderModelLiveData : StateFlow<FilterByGenderModel> = _filterByGenderModelLiveData










}