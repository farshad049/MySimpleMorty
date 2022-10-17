package com.farshad.mysimplemorty.ui.filter

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.mysimplemorty.epoxy.FilterEpoxyModel
import com.farshad.mysimplemorty.epoxy.HeaderEpoxyModel
import kotlinx.coroutines.launch

class FilterEpoxyController(
  val viewModel : FilterViewModel
):TypedEpoxyController<CombinedUiFilterModel>() {

    override fun buildModels(data: CombinedUiFilterModel) {

        HeaderEpoxyModel("status").id("filter_base_on_status").addTo(this)

        data.filterByStatus.forEach { statusFilter->
            FilterEpoxyModel(statusFilter , ::onStatusFilterClick).id(statusFilter.filterDisplayName).addTo(this)
        }

        HeaderEpoxyModel("gender").id("filter_base_on_gender").addTo(this)

        data.filterByGender.forEach { genderFilter->
            FilterEpoxyModel(genderFilter , ::onGenderFilterClick).id("gender${genderFilter.filterDisplayName}").addTo(this)

        }

    }

    private fun onStatusFilterClick(selectedFilter : String){
        viewModel.viewModelScope.launch {
            val currentSelectedFilter = viewModel.filterByStatusModelLiveData.value

            val newFilter =  currentSelectedFilter.copy(
                selectedStatus = if(currentSelectedFilter.selectedStatus.contains(selectedFilter)){
                    currentSelectedFilter.selectedStatus.filter { it != selectedFilter }.toSet()
                }else{
                    currentSelectedFilter.selectedStatus + setOf(selectedFilter)
                }
            )
            viewModel._filterByStatusModelLiveData.value = newFilter
        }
    }


    private fun onGenderFilterClick(selectedFilter : String){
        viewModel.viewModelScope.launch {
            val currentSelectedFilter = viewModel.filterByGenderModelLiveData.value

            val newFilter =  currentSelectedFilter.copy(
                selectedSpecies = if(currentSelectedFilter.selectedSpecies.contains(selectedFilter)){
                    currentSelectedFilter.selectedSpecies.filter { it != selectedFilter }.toSet()
                }else{
                    currentSelectedFilter.selectedSpecies + setOf(selectedFilter)
                }
            )
            viewModel._filterByGenderModelLiveData.value = newFilter
        }
    }




}