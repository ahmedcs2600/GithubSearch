package com.app.githubsearch.fakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.app.common.DataState
import com.app.domain.GithubRepository
import com.app.domain.models.User
import com.app.domain.models.UserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGithubRepository : GithubRepository {
    override fun searchUsers(keyword: String): LiveData<PagingData<User>> {
        val users = listOf(
            User(
                id = 1,
                login = "ahmed",
                type = "user",
                avatarUrl = "google.com",
                score = 5,
            ),
            User(
                id = 2,
                login = "jake",
                type = "user",
                avatarUrl = "google.com",
                score = 10,
            )
        )
        return MutableLiveData(PagingData.from(users))
    }

    override fun userDetails(userName: String): Flow<DataState<UserDetail>> {
        val details = UserDetail(
            login = "ahmed",
            id = 1,
            avatarUrl = "google.com",
            url = "google.com",
            company = "IT",
            location = "Karachi",
            email = "ahmedcs2600@gmail.com",
            bio = "Test Bio",
            htmlUrl = "google.com",
            followingUrl = "google.com",
            followersUrl = "google.com",
            reposUrl = "google.com",
            eventsUrl = "google.com",
        )
        return flowOf(DataState.success(details))
    }

}