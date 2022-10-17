package com.farshad.mysimplemorty.ui.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.farshad.mysimplemorty.BaseFragment
import com.farshad.mysimplemorty.NavGraphDirections
import com.farshad.mysimplemorty.R

import com.farshad.mysimplemorty.databinding.FragmentEpisodeListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class EpisodeListFragment: BaseFragment(R.layout.fragment_episode_list) {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel:EpisodesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)

        val epoxyController=EpisodeListEpoxyController(:: onEpisodeClick)

        //flow collect needs to run inside coroutine
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                epoxyController.submitData(it)
            }
        }

        binding.epoxyRecyclerView.setController(epoxyController)

       // throw RuntimeException("for fire base")
























    }//FUN

    private fun onEpisodeClick(episodeId:Int){
        val directions=NavGraphDirections.actionGlobalToEpisodeDetailFragment(episodeId)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}