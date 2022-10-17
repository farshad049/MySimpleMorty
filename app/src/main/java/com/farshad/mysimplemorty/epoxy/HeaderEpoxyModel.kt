package com.farshad.mysimplemorty.epoxy


import com.farshad.mysimplemorty.R
import com.farshad.mysimplemorty.databinding.ModelHeaderTitleBinding

data class HeaderEpoxyModel(val title:String)
    :ViewBindingKotlinModel<ModelHeaderTitleBinding>(R.layout.model_header_title){
    override fun ModelHeaderTitleBinding.bind() {
        tvHeaderTitle.text=title
    }
    //let this to take whole span count
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}