package com.example.mysimplemorty.episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.mysimplemorty.R
import com.example.mysimplemorty.databinding.ModelEpisodeListItemBinding
import com.example.mysimplemorty.domain.models.Character
import com.example.mysimplemorty.domain.models.Episode
import com.example.mysimplemorty.epoxy.ViewBindingKotlinModel

class EpisodeListEpoxyController(
  //  val onEpisodeClick:(Int) -> Unit
): PagingDataEpoxyController<Episode>() {
    override fun buildItemModel(currentPosition: Int, item: Episode?): EpoxyModel<*> {
        return EpisodeEpoxyModel(item!!, onClick = {})
            .id(item.id)




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


}