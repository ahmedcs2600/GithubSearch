package com.app.network.datasource

import com.app.common.DataState
import com.app.network.model.SearchUserResponse
import kotlinx.coroutines.flow.Flow

interface GithubRemoteDataSource {

    suspend fun searchUsers(
        query: String,
        sort: String,
        page: Int,
        perPage: Int
    ): Flow<DataState<SearchUserResponse>>
}