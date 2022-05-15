package com.app.githubsearch.components.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.app.githubsearch.components.home.HomeContract
import com.app.githubsearch.components.home.adapter.viewholder.HomeLoadStateViewHolder
import com.app.githubsearch.databinding.LayoutHomeLoadStateItemBinding
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class HomeLoadStateAdapter : LoadStateAdapter<HomeLoadStateViewHolder>() {

    private val _clicks = MutableSharedFlow<HomeContract.UIEvent>(extraBufferCapacity = 1,onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val clicks : SharedFlow<HomeContract.UIEvent> get() = _clicks

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): HomeLoadStateViewHolder {
        val binding = LayoutHomeLoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeLoadStateViewHolder(binding) {
            _clicks.tryEmit(HomeContract.UIEvent.Retry)
        }
    }

    override fun onBindViewHolder(holder: HomeLoadStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState)
    }
}