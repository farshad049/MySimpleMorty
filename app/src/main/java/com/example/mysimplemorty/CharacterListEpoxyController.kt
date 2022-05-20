package com.example.mysimplemorty

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.mysimplemorty.databinding.ModelCharacterListModelBinding
import com.example.mysimplemorty.databinding.ModelCharacterListTitleBinding
import com.example.mysimplemorty.epoxy.LoadingEpoxyModel
import com.example.mysimplemorty.epoxy.ViewBindingKotlinModel
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterListEpoxyController(
    private val onCharacterClick:(Int) -> Unit
):PagedListEpoxyController<GetCharacterByIdResponse>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItem(item!!,onCharacterClick)
            .id(item.id)
    }

    //we have this in PagedListEpoxyController because don't implement required list in this file
    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        //add first five character with its header
        Header("Main Family").id("main_family_header").addTo(this)
        super.addModels(models.subList(0,5))

       (models.subList(5,models.size) as List<CharacterGridItem>).groupBy {
           it.item.name[0].uppercaseChar()
       }.forEach {
           //group by return a a map <key , List<item>>, key is this "it.item.name[0].uppercaseChar()" and the list is "models.subList(5,models.size) as List<CharacterGridItem>"
           //we did this to could be able to use it ad id, because id should be unique because we are using it to grouping by
            val key=it.key.toString().uppercase()
            Header(key).id(key).addTo(this)
           super.addModels(it.value)
       }
    }




    data class CharacterGridItem(val item:GetCharacterByIdResponse,val onClick:(Int)->Unit)
        :ViewBindingKotlinModel<ModelCharacterListModelBinding>(R.layout.model_character_list_model) {
        override fun ModelCharacterListModelBinding.bind() {
            tvCharacterName.text=item.name
            Picasso.get().load(item.image).into(ivCharacterImage)
            root.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    data class Header(val title:String)
        :ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title){
        override fun ModelCharacterListTitleBinding.bind() {
            tvHeaderTitle.text=title
        }
        //let this to take whole span count
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

    }


}