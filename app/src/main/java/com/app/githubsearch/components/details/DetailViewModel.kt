package com.app.githubsearch.components.details

import androidx.lifecycle.*
import com.app.common.DataState
import com.app.domain.interactors.UserDetailUsecase
import com.app.githubsearch.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mUserDetailUsecase: UserDetailUsecase
) : BaseViewModel<DetailContract.UIState, DetailContract.UIEvent, DetailContract.UIEffect>() {

    private val _uiState = MutableLiveData<DetailContract.UIState>()
    override val uiState: LiveData<DetailContract.UIState> get() = _uiState

    override fun dispatchEvent(event: DetailContract.UIEvent) {
        when (event) {
            is DetailContract.UIEvent.LoadUserDetail -> {
                getUserDetails(event.userName)
            }
            is DetailContract.UIEvent.OpenBrowser -> {
                setEffect(DetailContract.UIEffect.OpenBrowser(event.url))
            }
        }
    }

    private fun getUserDetails(name: String) {
        mUserDetailUsecase.invoke(name).onEach { state ->
            when (state) {
                is DataState.Error -> {
                    setEffect(DetailContract.UIEffect.Error(state.message))
                }
                is DataState.Success -> {
                    _uiState.value = DetailContract.UIState.UserDetailLoaded(state.data)
                }
            }
        }.onStart {
            _uiState.value = DetailContract.UIState.Loading
        }.launchIn(viewModelScope)
    }

}