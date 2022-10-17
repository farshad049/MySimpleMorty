package com.farshad.mysimplemorty.ui.characters.search

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.farshad.mysimplemorty.R
import com.farshad.mysimplemorty.databinding.ModelCharacterListModelBinding
import com.farshad.mysimplemorty.databinding.ModelExeptionErrorBinding
import com.farshad.mysimplemorty.domain.models.Character
import com.farshad.mysimplemorty.epoxy.LoadingEpoxyModel
import com.farshad.mysimplemorty.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class CharacterSearchEpoxyController(
    private val onCharacterSelect:(Int)-> Unit
):PagingDataEpoxyController<Character>() {

    //when user didn't type any thing yet in search area ,do this
     var localException:CharacterSearchPagingSource.LocalException?=null
    set(value) {
        field=value
        if (localException !=null){
            //if localException is not null .immediately run "addModels" block of code,otherwise it will run "buildItemModel"
            requestForcedModelBuild()
        }
    }

    override fun buildItemModel(currentPosition: Int, item: Character?): EpoxyModel<*> {
        return SearchedCharacterModel(item!!,onCharacterSelect)
            .id(item.id)
    }

    //if we have an exception show it, otherwise continue the code
    override fun addModels(models: List<EpoxyModel<*>>) {
        if (localException != null){
            //show error information
            LocalExceptionErrorEpoxyModel(localException!!).id("error_state").addTo(this)
            return
        }

        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        super.addModels(models)
    }



    data class SearchedCharacterModel(val item:Character,val onClick:(Int)->Unit)
        :ViewBindingKotlinModel<ModelCharacterListModelBinding>(R.layout.model_character_list_model) {
        override fun ModelCharacterListModelBinding.bind() {
            tvCharacterName.text=item.name
            Picasso.get().load(item.image).into(ivCharacterImage)
            root.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    data class LocalExceptionErrorEpoxyModel(val localException:CharacterSearchPagingSource.LocalException)
        :ViewBindingKotlinModel<ModelExeptionErrorBinding>(R.layout.model_exeption_error){
        override fun ModelExeptionErrorBinding.bind() {
            tvErrorTitle.text=localException.title
            tvErrorDescription.text=localException.description
        }
        //let this to take whole span count
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

    }
}