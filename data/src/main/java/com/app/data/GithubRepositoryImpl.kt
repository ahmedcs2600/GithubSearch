package com.app.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.app.common.DataState
import com.app.data.mapper.UserDetailEntityUserDetailMapper
import com.app.domain.GithubRepository
import com.app.domain.models.User
import com.app.domain.models.UserDetail
import com.app.network.datasource.GithubRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val factory: UserSearchPagingFactory,
    private val remoteSource: GithubRemoteDataSource,
    private val mUserDetailEntityUserDetailMapper : UserDetailEntityUserDetailMapper
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

    override fun userDetails(userName: String): Flow<DataState<UserDetail>> {
        return remoteSource.userDetails(userName).map {
            return@map when (it) {
                is DataState.Error -> DataState.error(it.message)
                is DataState.Success -> DataState.success(
                    mUserDetailEntityUserDetailMapper.mapTo(it.data)
                )
            }
        }
    }
}