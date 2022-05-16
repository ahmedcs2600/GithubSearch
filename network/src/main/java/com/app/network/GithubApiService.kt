package com.app.network

import com.app.network.model.SearchUserResponse
import com.app.network.model.UserDetailEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<SearchUserResponse>

    @GET("users/{userName}")
    suspend fun userDetails(
        @Path("userName") userName: String
    ): Response<UserDetailEntity>
}