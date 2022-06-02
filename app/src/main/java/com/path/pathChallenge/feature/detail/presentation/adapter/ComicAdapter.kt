package com.path.pathChallenge.feature.detail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.path.data.remote.response.ComicsResponse
import com.path.pathChallenge.core.loadImage
import com.path.pathChallenge.core.platform.BaseListAdapter
import com.path.pathChallenge.core.platform.BaseViewHolder
import com.path.pathChallenge.databinding.ViewholderComicsBinding
import com.path.pathChallenge.feature.detail.domain.CharacterDetailViewModel

class ComicAdapter(
    val viewModel: CharacterDetailViewModel,
    private val detail: List<ComicsResponse.Data.Result>
) : BaseListAdapter<String>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return CharacterDetailViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CharacterDetailViewHolder -> {
                if (!detail.isNullOrEmpty()) {
                    holder.bind(detail[position], viewModel)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return detail.size
    }
}

class CharacterDetailViewHolder(val parent: ViewGroup, inflater: LayoutInflater) :
    BaseViewHolder<ViewholderComicsBinding>(
        binding = ViewholderComicsBinding.inflate(inflater, parent, false)
    ) {
    fun bind(
        detail: ComicsResponse.Data.Result,
        viewModel: CharacterDetailViewModel
    ) {

        binding.apply {
            this.viewModel = viewModel
            this.item = detail
            this.imageComics.loadImage(detail.thumbnail.path + "." + detail.thumbnail.extension)
            executePendingBindings()
        }
    }
}
