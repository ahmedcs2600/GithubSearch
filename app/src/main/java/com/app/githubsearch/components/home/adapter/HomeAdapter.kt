package com.app.githubsearch.components.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.app.domain.models.User
import com.app.githubsearch.components.home.adapter.viewholder.HomeViewHolder
import com.app.githubsearch.databinding.LayoutHomeItemBinding
import com.app.githubsearch.components.home.HomeContract
import com.app.githubsearch.components.home.adapter.diff.HomeDiffComparator
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class HomeAdapter : PagingDataAdapter<User, HomeViewHolder>(HomeDiffComparator()) {

    private val _clicks = MutableSharedFlow<HomeContract.UIEvent>(extraBufferCapacity = 1,onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val clicks : SharedFlow<HomeContract.UIEvent> get() = _clicks

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = LayoutHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding) {
            _clicks.tryEmit(HomeContract.UIEvent.NavigateToDetails(it.login))
        }
    }
}