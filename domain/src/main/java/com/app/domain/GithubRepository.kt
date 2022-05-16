package com.app.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.app.common.DataState
import com.app.domain.models.User
import com.app.domain.models.UserDetail
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun searchUsers(keyword : String): LiveData<PagingData<User>>

    fun userDetails(userName : String) : Flow<DataState<UserDetail>>
}