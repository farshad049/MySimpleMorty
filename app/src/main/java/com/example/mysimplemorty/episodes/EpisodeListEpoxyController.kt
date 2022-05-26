package com.example.mysimplemorty.episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.mysimplemorty.R
import com.example.mysimplemorty.characters.CharacterListEpoxyController
import com.example.mysimplemorty.databinding.ModelCharacterListTitleBinding
import com.example.mysimplemorty.databinding.ModelEpisodeListItemBinding
import com.example.mysimplemorty.domain.models.Character
import com.example.mysimplemorty.domain.models.Episode
import com.example.mysimplemorty.epoxy.ViewBindingKotlinModel

class EpisodeListEpoxyController(
    private val onEpisodeClick:(Int) -> Unit
): PagingDataEpoxyController<Episode>() {
    override fun buildItemModel(currentPosition: Int, item: Episode?): EpoxyModel<*> {
        return EpisodeEpoxyModel(item!!, onEpisodeClick)
            .id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        (models as List<EpisodeEpoxyModel>).groupBy {
            it.item.seasonNumber
        }.forEach {
            //group by return a a map <key , List<item>>, key is this "it.item.name[0].uppercaseChar()" and the list is "models.subList(5,models.size) as List<CharacterGridItem>"
            //we did this to could be able to use it ad id, because id should be unique because we are using it to grouping by
            val key = it.key.toString().uppercase()
            Header(key).id(key).addTo(this)
            super.addModels(it.value)
        }
    }






    data class EpisodeEpoxyModel(val item:Episode,val onClick: (Int)-> Unit)
        :ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item){
        override fun ModelEpisodeListItemBinding.bind() {
            tvEpisodeName.text=item.name
            tvAirDate.text=item.airDate
            tvEpisodeNumber.text=item.episode
            root.setOnClickListener { onClick(item.id) }
        }
    }

    data class Header(val title:String)
        :ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title){
        override fun ModelCharacterListTitleBinding.bind() {
            tvHeaderTitle.text="Season ${title}"
        }
        //let this to take whole span count
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

    }


}