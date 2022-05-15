package com.app.network.datasource

import com.app.common.DataState
import com.app.network.GithubApiService
import com.app.network.core.NetworkBoundRepository
import com.app.network.model.SearchUserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GithubRemoteDataSourceImpl @Inject constructor(private val apiService: GithubApiService) :
    GithubRemoteDataSource {

    override suspend fun searchUsers(
        query: String,
        sort: String,
        page: Int,
        perPage: Int
    ): Flow<DataState<SearchUserResponse>> {
        return object : NetworkBoundRepository<SearchUserResponse>() {
            override suspend fun fetchFromRemote(): Response<SearchUserResponse> {
                return apiService.searchUsers(query, sort, page, perPage)
            }
        }.asFlow()
    }
}