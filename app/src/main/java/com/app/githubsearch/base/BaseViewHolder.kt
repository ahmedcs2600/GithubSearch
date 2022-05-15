package com.app.githubsearch.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VIEW : ViewBinding, DATA>(private val binding : VIEW) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(item : DATA)

}