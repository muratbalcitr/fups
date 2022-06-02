package com.path.pathChallenge.feature.detail.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.path.data.remote.response.ComicsResponse
import com.path.pathChallenge.BuildConfig
import com.path.pathChallenge.R
import com.path.pathChallenge.core.common.PageName
import com.path.pathChallenge.core.extensions.HorizontalLinearLayoutManager
import com.path.pathChallenge.core.extensions.observe
import com.path.pathChallenge.core.loadImage
import com.path.pathChallenge.core.platform.BaseFragment
import com.path.pathChallenge.databinding.FragmentCharacterDetailBinding
import com.path.pathChallenge.feature.detail.domain.CharacterDetailViewModel
import com.path.pathChallenge.feature.detail.presentation.adapter.ComicAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>(
        layoutId = R.layout.fragment_character_detail,
        viewModelClass = CharacterDetailViewModel::class.java
    ) {
    override fun getScreenKey(): String {
        return PageName.characterDetail
    }

    private lateinit var comicAdapter: ComicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailId = arguments?.getString("detailId")
        viewModel.detailId.postValue(detailId)
        if (detailId != null) {
            viewModel.fetchMarvelDetailList(BuildConfig.PUBLIC_KEY, detailId)
            viewModel.marvelCharacterComics(BuildConfig.PUBLIC_KEY, detailId)
        }
    }

    override fun onDataBinding() {
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        observe(viewModel.characterDetail) {
            binding.characterImageBackground.loadImage(it.data.results[0].thumbnail.path + "." + it.data.results[0].thumbnail.extension)
        }
        observe(viewModel.comicDetail) {
            initAdapter(it.data.results)
        }
    }

    private fun initAdapter(results: List<ComicsResponse.Data.Result>) {
        comicAdapter = ComicAdapter(viewModel, results)
        binding.apply {
            recyclerviewComics.adapter = comicAdapter
            recyclerviewComics.layoutManager = HorizontalLinearLayoutManager(requireContext(), 0.4)
            recyclerviewComics.setHasFixedSize(true)
        }
    }
}
