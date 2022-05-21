package com.example.mysimplemorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mysimplemorty.databinding.FragmentCharacterDetailBinding

import com.example.mysimplemorty.databinding.FragmentEpisodeListBinding

class EpisodeListFragment: Fragment() {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEpisodeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)

        // todo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}