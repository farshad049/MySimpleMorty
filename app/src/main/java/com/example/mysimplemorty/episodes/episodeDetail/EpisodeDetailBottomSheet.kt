package com.example.mysimplemorty.episodes.episodeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mysimplemorty.databinding.FragmentEpisodeDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EpisodeDetailBottomSheet:BottomSheetDialogFragment() {
    private var _binding: FragmentEpisodeDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel:EpisodeDetailViewModel by viewModels()
    private val safeArgs:EpisodeDetailBottomSheetArgs by navArgs()
    private val controller=EpisodeDetailEpoxyController()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)

        viewModel.fetchEpisode(safeArgs.episodeId)

        viewModel.episodeByIdLiveData.observe(viewLifecycleOwner){
            if (it==null){
                Toast.makeText(requireContext(),"not successful", Toast.LENGTH_SHORT).show()
                //be sure to return and not continue the rest of function
                return@observe
                findNavController().navigateUp()
            }
            binding.tvSeasonEpisode.text="Season ${it?.seasonNumber} Episode ${it?.episodeNumber}"
            binding.tvdate.text=it?.airDate
            binding.tvEpisodeName.text=it?.name
            controller.items=it

        }






    }//FUN








    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}