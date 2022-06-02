package com.path.pathChallenge.feature.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.path.data.remote.response.CharactersResponse
import com.path.pathChallenge.core.loadImage
import com.path.pathChallenge.core.platform.BaseListAdapter
import com.path.pathChallenge.core.platform.BaseViewHolder
import com.path.pathChallenge.databinding.ViewholderCharacterListBinding
import com.path.pathChallenge.feature.main.domain.MarvelListViewModel

class MarvelListAdapter(
    val viewModel: MarvelListViewModel,
    val list: List<CharactersResponse.Data.Result>
) : BaseListAdapter<CharactersResponse.Data.Result>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return CarListAdapterViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CarListAdapterViewHolder -> {
                list.get(position).let { holder.bind(viewModel, it) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class CarListAdapterViewHolder(inflater: LayoutInflater, val parent: ViewGroup) :
    BaseViewHolder<ViewholderCharacterListBinding>(
        binding = ViewholderCharacterListBinding.inflate(inflater, parent, false)
    ) {
    fun bind(
        viewModels: MarvelListViewModel,
        items: CharactersResponse.Data.Result
    ) {
        binding.item = items
        binding.viewModel = viewModels
        binding.imageView.loadImage(items.thumbnail.path + "." + items.thumbnail.extension)
        binding.executePendingBindings()
    }
}
