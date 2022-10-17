package com.farshad.mysimplemorty.epoxy

import com.farshad.mysimplemorty.R
import com.farshad.mysimplemorty.databinding.ModelFilterBinding
import com.farshad.mysimplemorty.ui.filter.UiFilter


data class FilterEpoxyModel(
    val uiFilter: UiFilter,
    val onFilterClick:(String) -> Unit
)
    :ViewBindingKotlinModel<ModelFilterBinding>(R.layout.model_filter){
    override fun ModelFilterBinding.bind() {
        tvFilterName.text=uiFilter.filterDisplayName
        root.setOnClickListener { onFilterClick(uiFilter.filterDisplayName) }
        //checkbox.isClickable=false
        checkbox.isChecked = uiFilter.isSelected

    }

}

