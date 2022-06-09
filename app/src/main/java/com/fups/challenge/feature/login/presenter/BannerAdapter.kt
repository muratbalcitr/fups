package com.fups.challenge.feature.login.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fups.challenge.core.platform.BaseListAdapter
import com.fups.challenge.core.platform.BaseViewHolder
import com.fups.challenge.databinding.ViewholderBannerBinding
import com.fups.challenge.feature.login.domain.LoginViewModel
import com.fups.data.remote.response.BannerResponse

class BannerAdapter(
    val viewModel: LoginViewModel,
    val list: List<BannerResponse>?
) : BaseListAdapter<BannerResponse>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return BannerViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder -> {
                list?.get(position)?.let { holder.bind(viewModel, it) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}

class BannerViewHolder(inflater: LayoutInflater, val parent: ViewGroup) :
    BaseViewHolder<ViewholderBannerBinding>(
        binding = ViewholderBannerBinding.inflate(inflater, parent, false)
    ) {
    fun bind(
        viewModels: LoginViewModel,
        items: BannerResponse,
     ) {

        binding.item = items
        binding.viewModel = viewModels
        binding.bannerImageView.setImageResource(items.image)
        binding.executePendingBindings()
    }
}
