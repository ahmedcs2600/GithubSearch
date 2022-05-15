package com.app.githubsearch.components.home.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.app.domain.models.User

class HomeDiffComparator : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}