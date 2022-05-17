package com.app.githubsearch.components.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubsearch.R
import com.app.githubsearch.base.BaseFragment
import com.app.githubsearch.components.home.adapter.HomeAdapter
import com.app.githubsearch.components.home.adapter.HomeLoadStateAdapter
import com.app.githubsearch.databinding.FragmentHomeBinding
import com.app.githubsearch.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val mViewModel by viewModels<HomeViewModel>()

    private lateinit var mAdapter: HomeAdapter

    private lateinit var mHeaderHomeLoadStateAdapter : HomeLoadStateAdapter

    private lateinit var mFooterHomeLoadStateAdapter : HomeLoadStateAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideActionBar()
        setUpAdapter()
        bindViewModel()
        bindEvents()
    }

    private fun bindQueryChangeEvent(): Flow<HomeContract.UIEvent> {
        return bi.searchView.bindOnQueryTextListener().map {
            HomeContract.UIEvent.Search(it)
        }.onEach {
            bi.searchView.clearFocus()
            bi.recyclerView.scrollToPosition(0)
        }
    }

    private fun bindRetryButtonEvent(): Flow<HomeContract.UIEvent> {
        return bi.errorRetryButton.clicks().map { HomeContract.UIEvent.Retry }
    }

    private fun bindEvents() {
        uiEvents().onEach { mViewModel.dispatchEvent(it) }.launchIn(lifecycleScope)
        mAdapter.bindLoadStateListener().onEach {
            bi.progressBar.isVisible = it.refresh is LoadState.Loading
            bi.errorRetryButton.isVisible = it.refresh is LoadState.Error && mAdapter.itemCount <= 0

            if(it.refresh is LoadState.Error) {
                (it.refresh as LoadState.Error).error.message?.let { it1 ->
                    requireContext().toast(
                        it1
                    )
                }
            }

        }.launchIn(lifecycleScope)
    }

    private fun uiEvents(): Flow<HomeContract.UIEvent> {
        return merge(bindQueryChangeEvent(), mAdapter.clicks,mHeaderHomeLoadStateAdapter.clicks, mFooterHomeLoadStateAdapter.clicks, bindRetryButtonEvent())
    }

    private fun bindViewModel() {
        with(mViewModel) {
            uiState.observe(viewLifecycleOwner) { data ->
                when(data) {
                    is HomeContract.UIState.SearchedResult -> {
                        mAdapter.submitData(viewLifecycleOwner.lifecycle, data.data)
                    }
                }
            }

            uiEffect.observe(viewLifecycleOwner, EventObserver { effect ->
                when (effect) {
                    HomeContract.UIEffect.SearchedQueryEmpty -> {
                        requireContext().toast(R.string.query_empty_error)
                    }

                    is HomeContract.UIEffect.NavigateToDetails -> {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                effect.name
                            )
                        )
                    }
                    HomeContract.UIEffect.Retry -> {
                        mAdapter.retry()
                    }
                }
            })
        }
    }


    private fun setUpAdapter() {
        mHeaderHomeLoadStateAdapter = HomeLoadStateAdapter()
        mFooterHomeLoadStateAdapter = HomeLoadStateAdapter()

        mAdapter = HomeAdapter()

        bi.recyclerView.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = mHeaderHomeLoadStateAdapter,
            footer = mFooterHomeLoadStateAdapter
        )

        bi.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}