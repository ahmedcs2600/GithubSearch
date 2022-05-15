package com.app.githubsearch.components.home.adapter.viewholder

import coil.load
import coil.transform.CircleCropTransformation
import com.app.domain.models.User
import com.app.githubsearch.R
import com.app.githubsearch.base.BaseViewHolder
import com.app.githubsearch.databinding.LayoutHomeItemBinding

class HomeViewHolder(private val binding: LayoutHomeItemBinding, private val block : (user : User) -> Unit) :
    BaseViewHolder<LayoutHomeItemBinding, User>(binding) {

    override fun onBind(item: User) {
        with(binding) {
            parentCard.setOnClickListener { block.invoke(item) }
            image.load(item.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            title.text = item.login
            score.text = score.context.getString(R.string.score_label, item.score.toString())
        }
    }
}