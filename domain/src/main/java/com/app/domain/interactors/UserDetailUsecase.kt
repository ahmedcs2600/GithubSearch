package com.app.domain.interactors

import com.app.common.DataState
import com.app.domain.GithubRepository
import com.app.domain.models.UserDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailUsecase @Inject constructor(private val repository: GithubRepository) {
    operator fun invoke(userName : String): Flow<DataState<UserDetail>> {
        return repository.userDetails(userName)
    }
}