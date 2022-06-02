package com.path.pathChallenge.feature.main.presentation

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.path.data.remote.response.CharactersResponse
import com.path.pathChallenge.BuildConfig
import com.path.pathChallenge.R
import com.path.pathChallenge.core.bindings.visible
import com.path.pathChallenge.core.common.PageName
import com.path.pathChallenge.core.common.PaginationScrollListener
import com.path.pathChallenge.core.event.FirebaseHelper
import com.path.pathChallenge.core.extensions.observe
import com.path.pathChallenge.core.extensions.observeEvent
import com.path.pathChallenge.core.platform.BaseFragment
import com.path.pathChallenge.databinding.FragmentCharacterListBinding
import com.path.pathChallenge.feature.main.domain.MarvelListViewEvent
import com.path.pathChallenge.feature.main.domain.MarvelListViewModel
import com.path.pathChallenge.feature.main.presentation.adapter.MarvelListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarvelListFragment : BaseFragment<FragmentCharacterListBinding, MarvelListViewModel>(
    layoutId = R.layout.fragment_character_list,
    viewModelClass = MarvelListViewModel::class.java
) {

    private var isLoadings = true
    lateinit var marvelListAdapter: MarvelListAdapter
    private lateinit var dropdownSortest: ArrayAdapter<String?>

    override fun getScreenKey(): String {
        return PageName.characterList
    }

    var totalCount = 0
    var limit = 30
    override fun onDataBinding() {

        viewModel.fetchMarvelList(
            BuildConfig.PUBLIC_KEY,
            limit = limit,
            orderBy = "name"
        )
        observeEvent(viewModel.event, ::onViewEvent)
        observe(viewModel.chrachterResponse) {
            totalCount = it.data.count
            initAdapter(it.data.results)
        }
        observe(viewModel.isMoreLoading) {
            binding.progressBar.visible = it
        }
    }

    fun initAdapter(results: List<CharactersResponse.Data.Result>) {
        marvelListAdapter = MarvelListAdapter(viewModel, results)
        binding.carListRecyclerView.adapter = marvelListAdapter
        val manager = LinearLayoutManager(requireContext())
        binding.carListRecyclerView.layoutManager = manager
        binding.carListRecyclerView.addOnScrollListener(
            object : PaginationScrollListener(manager) {
                override fun loadMoreItems() {
                    viewModel.setMoreLoading(true)
                    viewModel.fetchMarvelList(
                        BuildConfig.PUBLIC_KEY,
                        limit = limit + 30,
                        orderBy = "name"
                    )
                }
            }
        )
    }

    private fun onViewEvent(event: MarvelListViewEvent) {
        when (event) {
            is MarvelListViewEvent.SelectMarvel -> {
                logEventFirebase(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    FirebaseHelper.generateFirebaseEventParams(
                        characterId = event.item.id.toString(),
                        characterName = event.item.name
                    )
                )
                val bundle = Bundle()
                bundle.putString("detailId", event.item.id.toString())
                findNavController().navigate(R.id.action_characterList_to_characterDetail, bundle)
            }
        }
    }
}
