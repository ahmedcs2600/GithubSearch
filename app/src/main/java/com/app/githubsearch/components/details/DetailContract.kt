package com.app.githubsearch.components.details

import com.app.domain.models.UserDetail
import com.app.githubsearch.base.Effect
import com.app.githubsearch.base.Event
import com.app.githubsearch.base.State


class DetailContract {

    sealed class UIEffect : Effect {
        data class Error(val message : String) : UIEffect()
        data class OpenBrowser(val url : String?) : UIEffect()
    }

    sealed class UIState : State {
        data class UserDetailLoaded(val data : UserDetail) : UIState()
        object Loading : UIState()
    }

    sealed class UIEvent : Event {
        data class LoadUserDetail(val userName : String) : UIEvent()
        data class OpenBrowser(val url : String?) : UIEvent()
    }
}