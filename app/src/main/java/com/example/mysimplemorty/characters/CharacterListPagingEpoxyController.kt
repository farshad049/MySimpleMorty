package com.example.mysimplemorty.characters

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.mysimplemorty.R
import com.example.mysimplemorty.databinding.ModelCharacterListModelBinding
import com.example.mysimplemorty.epoxy.ViewBindingKotlinModel
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterListPagingEpoxyController:PagedListEpoxyController<GetCharacterByIdResponse>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItem(item!!).id(item.id)
    }



    data class CharacterGridItem(val item:GetCharacterByIdResponse)
        :ViewBindingKotlinModel<ModelCharacterListModelBinding>(R.layout.model_character_list_model) {
        override fun ModelCharacterListModelBinding.bind() {
            tvCharacterName.text=item.name
            Picasso.get().load(item.image).into(ivCharacterImage)
        }
    }


}