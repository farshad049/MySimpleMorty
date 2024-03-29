package com.farshad.mysimplemorty.ui.characters.characterDetail

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.farshad.mysimplemorty.R
import com.farshad.mysimplemorty.databinding.*
import com.farshad.mysimplemorty.domain.models.Character
import com.farshad.mysimplemorty.domain.models.Episode
import com.farshad.mysimplemorty.epoxy.LoadingEpoxyModel
import com.farshad.mysimplemorty.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController(
    val onEpisodeClick:(Int)->Unit
):EpoxyController() {

    var isLoading:Boolean=true
        set(value) {
            field=value
            if (field){
                requestModelBuild()
            }
        }

    var items: Character? = null
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
        Title("Episodes").id("episodes").addTo(this)

        //episode list carousel section
        if (items!!.episodeList.isNotEmpty()){
            val list=items!!.episodeList.map {
                EpisodeModel(it,onEpisodeClick).id(it.id)
            }
            CarouselModel_()
                .id("episode_carousel")
                .models(list)
                .numViewsToShowOnScreen(1.15f)
                .addTo(this)
        }

        DataPoint("Origin",items!!.origin.name).id("Origin").addTo(this)
        DataPoint("Species",items!!.species).id("Species").addTo(this)


    }









    data class Header(val item:Character)
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

    data class DataPoint(val title:String,val description:String)
        : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            tvTitle.text=title
            tvDescription.text= description
        }
    }

    data class Title(val title:String)
        :ViewBindingKotlinModel<ModelTitleBinding>(R.layout.model_title) {
        override fun ModelTitleBinding.bind() {
            tvEpisode.text=title
        }
    }

    data class EpisodeModel(val episode: Episode,val onClick:(Int)->Unit)
        :ViewBindingKotlinModel<ModelEpisodeCarouselItemBinding>(R.layout.model_episode_carousel_item) {
        override fun ModelEpisodeCarouselItemBinding.bind() {
            tvSeasonEpisode.text=episode.episode
            tvEpisodeName.text="${episode.name}\n${episode.airDate}"
            root.setOnClickListener { onClick(episode.id) }
        }
    }

}