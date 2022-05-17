package com.app.githubsearch.components.home.adapter.viewholder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.app.githubsearch.base.BaseViewHolder
import com.app.githubsearch.databinding.LayoutHomeLoadStateItemBinding

class HomeLoadStateViewHolder(private val binding: LayoutHomeLoadStateItemBinding, private val block : () -> Unit) : BaseViewHolder<LayoutHomeLoadStateItemBinding, LoadState>(binding) {

    init {
        binding.buttonRetry.bindListener { block() }
    }

    override fun onBind(item: LoadState) {
        with(binding) {

            if (item is LoadState.Error) {
                item.error.message?.let { binding.buttonRetry.setError(it) }
            }

            progressBar.isVisible = item is LoadState.Loading
            buttonRetry.isVisible = item is LoadState.Error
        }
    }
}