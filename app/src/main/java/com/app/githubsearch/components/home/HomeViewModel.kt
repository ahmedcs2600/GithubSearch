package com.app.githubsearch.components.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.app.domain.interactors.SearchUserUsecase
import com.app.githubsearch.base.BaseViewModel
import com.app.githubsearch.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mSearchUserUsecase: SearchUserUsecase) :
    BaseViewModel<HomeContract.UIState, HomeContract.UIEvent, HomeContract.UIEffect>() {

    private val _currentQuery = MutableLiveData("Jake")

    override val uiState: LiveData<HomeContract.UIState> =
        Transformations.switchMap(_currentQuery) { mSearchUserUsecase.invoke(it) }
            .cachedIn(viewModelScope).map { HomeContract.UIState.SearchedResult(it) }

    override fun dispatchEvent(event: HomeContract.UIEvent) {
        when (event) {
            is HomeContract.UIEvent.Search -> {
                onSearch(event.query)
            }
            is HomeContract.UIEvent.NavigateToDetails -> {
                onNavigateToDetails(event.userName)
            }
            HomeContract.UIEvent.Retry -> {
                setEffect(HomeContract.UIEffect.Retry)
            }
        }
    }

    private fun onNavigateToDetails(userName: String) {
        setEffect(HomeContract.UIEffect.NavigateToDetails(userName))
    }

    private fun onSearch(query: String) {
        if (query.isEmpty()) {
            setEffect(HomeContract.UIEffect.SearchedQueryEmpty)
            return
        }
        _currentQuery.value = query
    }
}