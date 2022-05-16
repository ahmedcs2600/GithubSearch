package com.app.data

import com.app.common.DataState
import com.app.common.Utils
import com.app.data.mapper.UserDetailEntityUserDetailMapper
import com.app.data.utils.getUserDetailEntity
import com.app.domain.models.UserDetail
import com.app.network.datasource.GithubRemoteDataSource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GithubRepositoryImplTest {

    private lateinit var mGithubRepositoryImpl: GithubRepositoryImpl

    @MockK
    lateinit var mUserSearchPagingFactory: UserSearchPagingFactory

    @MockK
    lateinit var mGithubRemoteDataSource: GithubRemoteDataSource

    @MockK(relaxed = true)
    lateinit var mUserDetailEntityUserDetailMapper: UserDetailEntityUserDetailMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mGithubRepositoryImpl = GithubRepositoryImpl(
            mUserSearchPagingFactory,
            mGithubRemoteDataSource,
            mUserDetailEntityUserDetailMapper
        )
    }

    @Test
    fun `test invokes searchUsers returns LiveData_PagingData_User`() {
        //Given
        val keyword = Utils.getRandomString()

        val pagingSource = mockk<UserSearchPagingSource>()

        every { mUserSearchPagingFactory.createUserSearchPaging(keyword) }.returns(pagingSource)

        //Invokes
        val result = mGithubRepositoryImpl.searchUsers(keyword)

        //Then
        verify(exactly = 1) { mUserSearchPagingFactory.createUserSearchPaging(keyword) }
    }

    @Test
    fun `test invokes userDetails returns DataState_Success`() = runBlocking {
        //Given
        val name = Utils.getRandomString()
        val userEntity = getUserDetailEntity()
        val user = UserDetail(
            login = userEntity.login,
            id = userEntity.id,
            avatarUrl = userEntity.avatarUrl,
            url = userEntity.url,
            company = userEntity.company,
            location = userEntity.location,
            email = userEntity.email,
            bio = userEntity.bio,
            htmlUrl = userEntity.htmlUrl,
            followingUrl = userEntity.followingUrl,
            followersUrl = userEntity.followersUrl,
            reposUrl = userEntity.reposUrl,
            eventsUrl = userEntity.eventsUrl,
        )

        every { mGithubRemoteDataSource.userDetails(name) }.returns(
            flowOf(
                DataState.success(
                    userEntity
                )
            )
        )
        every { mUserDetailEntityUserDetailMapper.mapTo(userEntity) }.returns(user)

        //Invokes
        val result = mGithubRepositoryImpl.userDetails(name).first()

        //Then
        coVerify(exactly = 1) { mGithubRemoteDataSource.userDetails(name) }
        coVerify(exactly = 1) { mUserDetailEntityUserDetailMapper.mapTo(userEntity) }

        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Success::class.java))
        MatcherAssert.assertThat((result as DataState.Success).data, CoreMatchers.`is`(user))
    }

    @Test
    fun `test invokes userDetails returns DataState_Error`() = runBlocking {
        //Given
        val name = Utils.getRandomString()
        val errorMessage = Utils.getRandomString()

        every { mGithubRemoteDataSource.userDetails(name) }.returns(
            flowOf(
                DataState.error(
                    errorMessage
                )
            )
        )

        //Invokes
        val result = mGithubRepositoryImpl.userDetails(name).first()

        //Then
        coVerify(exactly = 1) { mGithubRemoteDataSource.userDetails(name) }
        coVerify(exactly = 0) { mUserDetailEntityUserDetailMapper.mapTo(any()) }

        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Error::class.java))
        MatcherAssert.assertThat(
            (result as DataState.Error).message,
            CoreMatchers.`is`(errorMessage)
        )
    }
}