package com.app.network.datasource

import com.app.common.DataState
import com.app.network.model.SearchUserResponse
import com.app.network.model.UserDetailEntity
import kotlinx.coroutines.flow.Flow

interface GithubRemoteDataSource {

    suspend fun searchUsers(
        query: String,
        sort: String,
        page: Int,
        perPage: Int
    ): Flow<DataState<SearchUserResponse>>

    fun userDetails(userName: String): Flow<DataState<UserDetailEntity>>
}