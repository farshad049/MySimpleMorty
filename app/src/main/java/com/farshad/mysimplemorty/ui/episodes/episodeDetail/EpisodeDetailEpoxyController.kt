package com.farshad.mysimplemorty.ui.episodes.episodeDetail

import com.airbnb.epoxy.EpoxyController
import com.farshad.mysimplemorty.R
import com.farshad.mysimplemorty.databinding.ModelEpisodeDetailImageBinding
import com.farshad.mysimplemorty.domain.models.Character
import com.farshad.mysimplemorty.domain.models.Episode
import com.farshad.mysimplemorty.epoxy.LoadingEpoxyModel
import com.farshad.mysimplemorty.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class EpisodeDetailEpoxyController:EpoxyController() {

    var isLoading:Boolean=true
        set(value) {
            field=value
            if (field){
                requestModelBuild()
            }
        }

    var items: Episode? = null
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
        items?.character?.forEach {
            CarouselEpoxyModel(it).id(it.id).addTo(this)
        }
    }



    data class CarouselEpoxyModel(val item:Character)
        :ViewBindingKotlinModel<ModelEpisodeDetailImageBinding>(R.layout.model_episode_detail_image){
        override fun ModelEpisodeDetailImageBinding.bind() {
            Picasso.get().load(item.image).into(iv)
            tvCharacterName.text=item.name
        }

    }
}