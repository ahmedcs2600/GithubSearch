package com.app.githubsearch.components.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.app.domain.models.UserDetail
import com.app.githubsearch.R
import com.app.githubsearch.base.BaseFragment
import com.app.githubsearch.databinding.FragmentDetailBinding
import com.app.githubsearch.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val mViewModel by viewModels<DetailViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    private val arg : DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.dispatchEvent(DetailContract.UIEvent.LoadUserDetail(arg.username))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showActionBar()
        bindViewModel()
        bindClicks()
    }

    private fun bindClicks() {
        bi.browserButton.clicks().map {
            DetailContract.UIEvent.OpenBrowser(bi.browserButton.tag as? String)
        }.onEach {
            mViewModel.dispatchEvent(it)
        }.launchIn(lifecycleScope)
    }

    private fun bindViewModel() {
        with(mViewModel) {
            uiState.observe(viewLifecycleOwner) { state ->
                when (state) {

                    is DetailContract.UIState.UserDetailLoaded -> {
                        bindUI(state.data)
                    }

                    DetailContract.UIState.Loading -> {
                        bi.progressBar.isVisible = true
                        bi.contentContainer.isVisible = false
                    }
                }
            }

            uiEffect.observe(viewLifecycleOwner, EventObserver { effect ->
                when (effect) {
                    is DetailContract.UIEffect.Error -> {
                        requireContext().toast(effect.message)
                        bi.progressBar.isVisible = false
                    }

                    is DetailContract.UIEffect.OpenBrowser -> {
                        effect.url?.toUri?.let { requireContext().intentActionView(it) }
                    }
                }
            })
        }
    }

    private fun bindUI(data: UserDetail) {
        with(bi) {
            progressBar.isVisible = false
            contentContainer.isVisible = true
            image.load(data.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            title.text = data.login

            email.setTextVal(R.string.email_label, data.email)
            location.setTextVal(R.string.location_label, data.location)
            bio.setTextVal(R.string.bio_label, data.bio)
            followersUrl.setTextVal(R.string.followers_url_label, data.followersUrl)
            followingUrl.setTextVal(R.string.following_url_label, data.followingUrl)
            reposUrl.setTextVal(R.string.repos_url_label, data.reposUrl)
            eventsUrl.setTextVal(R.string.events_url_label, data.eventsUrl)
            browserButton.tag = data.htmlUrl
        }
    }
}