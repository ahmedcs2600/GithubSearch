package com.app.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.common.DataState
import com.app.data.mapper.UserEntityUserMapper
import com.app.domain.models.User
import com.app.network.datasource.GithubRemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

class UserSearchPagingSource @AssistedInject constructor(
    private val remote: GithubRemoteDataSource,
    private val mUserEntityUserMapper: UserEntityUserMapper,
    @Assisted("keyword") private val keyword: String
) : PagingSource<Int, User>() {

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
        const val PER_PAGE = 10
        private const val SORT_BY = "alphabetically"
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int {
        return INITIAL_PAGE_NUMBER
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        return when (val response =
            remote.searchUsers(keyword, SORT_BY, pageNumber, PER_PAGE).first()) {
            is DataState.Error -> {
                LoadResult.Error(Exception(response.message))
            }
            is DataState.Success -> {
                val items = response.data.items.map { mUserEntityUserMapper.mapTo(it) }
                LoadResult.Page(
                    data = items,
                    prevKey = if (pageNumber == INITIAL_PAGE_NUMBER) null else pageNumber - 1,
                    nextKey = if (items.isEmpty()) null else pageNumber + 1
                )
            }
        }
    }
}