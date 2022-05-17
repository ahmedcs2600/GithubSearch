package com.app.githubsearch.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.app.githubsearch.databinding.LayoutErrorRetryBinding
import com.app.githubsearch.utils.clicks

class ErrorRetryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding : LayoutErrorRetryBinding

    init {
        orientation = VERTICAL
        binding = LayoutErrorRetryBinding.inflate(LayoutInflater.from(context), this)
    }

    fun clicks() = binding.btnReload.clicks()

    fun bindListener(listener : OnClickListener) {
        binding.btnReload.setOnClickListener(listener)
    }

    fun setError(error : String) {
        binding.textViewError.text = error
    }
}