package com.app.domain.interactors

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.app.domain.GithubRepository
import com.app.domain.models.User
import javax.inject.Inject

class SearchUserUsecase @Inject constructor(private val repository: GithubRepository) {
    operator fun invoke(keyword: String): LiveData<PagingData<User>> {
        return repository.searchUsers(keyword)
    }
}