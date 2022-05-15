package com.app.githubsearch.base

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubsearch.utils.Event as SingleLiveDataEvent

abstract class BaseViewModel<STATE : State, EVENT : Event, EFFECT : Effect> : ViewModel() {

    private val _uiEffect = MutableLiveData<SingleLiveDataEvent<EFFECT>>()
    val uiEffect : LiveData<SingleLiveDataEvent<EFFECT>> get() = _uiEffect

    abstract val uiState : LiveData<STATE>

    abstract fun dispatchEvent(event : EVENT)

    @MainThread
    fun setEffect(event : EFFECT) {
        _uiEffect.value = SingleLiveDataEvent(event)
    }
}