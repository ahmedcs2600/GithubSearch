package com.app.githubsearch.components.home

import androidx.paging.PagingData
import com.app.domain.models.User
import com.app.githubsearch.base.Effect
import com.app.githubsearch.base.Event
import com.app.githubsearch.base.State

class HomeContract {
    sealed class UIEffect : Effect {
        object SearchedQueryEmpty : UIEffect()
        data class NavigateToDetails(val name : String) : UIEffect()
        object Retry : UIEffect()
    }

    sealed class UIEvent : Event {
        data class Search(val query : String) : UIEvent()
        data class NavigateToDetails(val userName : String) : UIEvent()
        object Retry : UIEvent()
    }

    sealed class UIState : State {
        data class SearchedResult(val data : PagingData<User>) : UIState()
    }
}