package com.davidson.gamesdb.ui.detailed

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.davidson.gamesdb.adapter.bindImage
import com.davidson.gamesdb.databinding.FragmentDetailBinding
import com.davidson.gamesdb.domain.DomainGameGist


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var gameItem: DomainGameGist

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val animationDetailedImage = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        ).also {
            it.duration = 1000L
        }

        sharedElementEnterTransition = animationDetailedImage
        sharedElementReturnTransition = animationDetailedImage

        val args: DetailFragmentArgs by navArgs()

        gameItem = args.gameItemDetailed!!

        populate()


        return binding.root
    }

    fun populate() {
        bindImage(binding.ivDetailedMainImage, gameItem.gameGistBgImage)
        binding.tvDetailedDevName.text = gameItem.gameGistRating.toString() + "★"
        binding.tvDetailedGameName.text = gameItem.gameGistName
        bindImage(binding.ivScreenShot1, gameItem.gameGistScreenshot1)
        bindImage(binding.ivScreenShot2, gameItem.gameGistScreenshot2)
        bindImage(binding.ivScreenShot3, gameItem.gameGistScreenshot3)
    }

}