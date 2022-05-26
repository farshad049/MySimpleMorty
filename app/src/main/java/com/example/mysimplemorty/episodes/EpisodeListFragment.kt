package com.example.mysimplemorty.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mysimplemorty.databinding.FragmentCharacterDetailBinding

import com.example.mysimplemorty.databinding.FragmentEpisodeListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeListFragment: Fragment() {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel:EpisodesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEpisodeListBinding.inflate(inflater, container, false)
        return binding.root
    }

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
























    }//FUN

    private fun onEpisodeClick(episodeId:Int){
        val directions=EpisodeListFragmentDirections.actionEpisodeListFragmentToEpisodeDetailFragment(episodeId)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}