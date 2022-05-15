package com.app.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.app.domain.GithubRepository
import com.app.domain.models.User
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val factory: UserSearchPagingFactory
) :
    GithubRepository {

    override fun searchUsers(keyword: String): LiveData<PagingData<User>> {
        val userPagingSource = factory.createUserSearchPaging(keyword)
        return Pager(
            config = PagingConfig(
                pageSize = UserSearchPagingSource.PER_PAGE,
                maxSize = UserSearchPagingSource.PER_PAGE * 5,
                enablePlaceholders = false
            ), pagingSourceFactory = { userPagingSource }).liveData
    }
}