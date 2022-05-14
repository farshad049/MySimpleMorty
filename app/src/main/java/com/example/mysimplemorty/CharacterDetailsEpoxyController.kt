package com.example.mysimplemorty

import com.airbnb.epoxy.EpoxyController
import com.example.mysimplemorty.databinding.ModelCharacterDetailsDataPointBinding
import com.example.mysimplemorty.databinding.ModelCharacterDetailsHeaderBinding
import com.example.mysimplemorty.databinding.ModelCharacterDetailsImageBinding
import com.example.mysimplemorty.epoxy.LoadingEpoxyModel
import com.example.mysimplemorty.epoxy.ViewBindingKotlinModel
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController:EpoxyController() {

    var isLoading:Boolean=false
        set(value) {
            field=value
            if (field){
                requestModelBuild()
            }
        }

    var items: GetCharacterByIdResponse? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading){
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }
        if (items == null) {
            // todo error state
            return
        }

        Header(items!!).id("header").addTo(this)
        Image(items!!.image).id("image").addTo(this)
        DataPoint("Origin",items!!).id("date").addTo(this)
        DataPoint("Species",items!!).id("date").addTo(this)

    }









    data class Header(val item:GetCharacterByIdResponse)
        : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
           tvName.text=item.name
            tvStatus.text=item.status
            if (item.gender.equals("male",false)){
                ivGender.setImageResource(R.drawable.ic_male_24)
            }else{
                ivGender.setImageResource(R.drawable.ic_female_24)
            }
        }
    }

    data class Image(val image:String)
        : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(image).into(ivHeader)
        }
    }

    data class DataPoint(val title:String,val item:GetCharacterByIdResponse)
        : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            tvTitle.text=title
            tvDescription.text= item.origin.name
        }
    }

}