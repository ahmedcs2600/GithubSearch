package com.app.data

import androidx.paging.*
import com.app.common.DataState
import com.app.common.Utils
import com.app.data.mapper.UserEntityUserMapper
import com.app.data.utils.getFakeUserEntity
import com.app.domain.models.User
import com.app.network.datasource.GithubRemoteDataSource
import com.app.network.model.SearchUserResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserSearchPagingSourceTest {

    @MockK
    lateinit var mRemote: GithubRemoteDataSource

    @MockK
    lateinit var mUserEntityUserMapper: UserEntityUserMapper

    private lateinit var mUserSearchPagingSource : UserSearchPagingSource


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mUserSearchPagingSource = UserSearchPagingSource(mRemote, mUserEntityUserMapper, "Jake")
    }

    @Test
    fun `test refresh load Returns Data, Next Page and Prev Key as null`() = runBlocking {
        //Given
        val dummyEntity = getFakeUserEntity()

        val dummyResponse =  SearchUserResponse(
            totalCount = Utils.getRandomInt(),
            incompleteResults = Utils.getRandomBoolean(),
            items = (1..5).map { dummyEntity })

        val user = User(
            id = dummyEntity.id,
            login = dummyEntity.login,
            type = dummyEntity.type,
            avatarUrl = dummyEntity.avatarUrl,
            score = dummyEntity.score,
        )


        every { mUserEntityUserMapper.mapTo(dummyEntity) }.returns(user)

        coEvery { mRemote.searchUsers(any(), any(), any(), any()) }.returns(flowOf(DataState.success(dummyResponse)))

        val expected = PagingSource.LoadResult.Page(
            data = (0..4).map { user },
            prevKey = null,
            nextKey = 2
        )

        //Invoke
        val actual = mUserSearchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 5,
                placeholdersEnabled = false
            )
        )

        //Then
        MatcherAssert.assertThat(expected, CoreMatchers.`is`(actual))
    }

    @Test
    fun `test append load Returns Empty Data ,Prev Key non null and Next Page as null`() = runBlocking {
        //Given
        val dummyResponse =  SearchUserResponse(
            totalCount = Utils.getRandomInt(),
            incompleteResults = Utils.getRandomBoolean(),
            items = listOf()
        )

        coEvery { mRemote.searchUsers(any(), any(), any(), any()) }.returns(flowOf(DataState.success(dummyResponse)))

        val expected = PagingSource.LoadResult.Page(
            data = listOf(),
            prevKey = 4,
            nextKey = null
        )

        //Invoke
        val actual = mUserSearchPagingSource.load(
            PagingSource.LoadParams.Append(
                key = 5,
                loadSize = 5,
                placeholdersEnabled = false
            )
        )

        //Then
        MatcherAssert.assertThat(expected, CoreMatchers.`is`(actual))
    }

    @Test
    fun `test append load Returns Empty Data ,NextPage Key null and Next Page as non null`() = runBlocking {
        //Given
        val dummyEntity = getFakeUserEntity()

        val dummyResponse =  SearchUserResponse(
            totalCount = Utils.getRandomInt(),
            incompleteResults = Utils.getRandomBoolean(),
            items = (1..5).map { dummyEntity })

        val user = User(
            id = dummyEntity.id,
            login = dummyEntity.login,
            type = dummyEntity.type,
            avatarUrl = dummyEntity.avatarUrl,
            score = dummyEntity.score,
        )


        every { mUserEntityUserMapper.mapTo(dummyEntity) }.returns(user)

        coEvery { mRemote.searchUsers(any(), any(), any(), any()) }.returns(flowOf(DataState.success(dummyResponse)))

        val expected = PagingSource.LoadResult.Page(
            data = (1..5).map { user },
            prevKey = null,
            nextKey = 2
        )

        //Invoke
        val actual = mUserSearchPagingSource.load(
            PagingSource.LoadParams.Prepend(
                key = 1,
                loadSize = 5,
                placeholdersEnabled = false
            )
        )

        //Then
        MatcherAssert.assertThat(expected, CoreMatchers.`is`(actual))
    }
}