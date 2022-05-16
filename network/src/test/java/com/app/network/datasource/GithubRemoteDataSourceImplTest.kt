package com.app.network.datasource

import com.app.common.DataState
import com.app.network.GithubApiService
import com.app.common.Utils
import com.app.network.core.NetworkErrorProvider
import com.app.network.getSearchUserFakeResponse
import com.app.network.getUserDetailEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class GithubRemoteDataSourceImplTest {

    @MockK(relaxed = true)
    lateinit var mNetworkErrorProvider : NetworkErrorProvider

    @MockK
    lateinit var mGithubApiService: GithubApiService

    lateinit var mGithubRemoteDataSourceImpl : GithubRemoteDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mGithubRemoteDataSourceImpl = GithubRemoteDataSourceImpl(mGithubApiService, mNetworkErrorProvider)
    }

    @Test
    fun `test searchUsers invokes mGithubApiService_searchUsers and returns DataState_success`() = runBlocking {
        //Given
        val query = Utils.getRandomString()
        val sort = Utils.getRandomString()
        val page = Utils.getRandomInt()
        val perPage = Utils.getRandomInt()

        val fakeResponse = getSearchUserFakeResponse()

        coEvery { mGithubApiService.searchUsers(query, sort, page, perPage) }.returns(
            Response.success(fakeResponse))

        //Invokes
        val result = mGithubRemoteDataSourceImpl.searchUsers(query, sort, page, perPage).first()

        //Then
        coVerify(exactly = 1) {  mGithubApiService.searchUsers(query, sort, page, perPage) }
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Success::class.java))
        MatcherAssert.assertThat((result as DataState.Success).data, CoreMatchers.`is`(fakeResponse))
    }

    @Test
    fun `test searchUsers invokes mGithubApiService_searchUsers and returns DataState_error`() = runBlocking {
        //Given
        val query = Utils.getRandomString()
        val sort = Utils.getRandomString()
        val page = Utils.getRandomInt()
        val perPage = Utils.getRandomInt()

        val errorMessage = "Something went wrong"

        every { mNetworkErrorProvider.somethingWentWrong() }.returns(errorMessage)

        coEvery { mGithubApiService.searchUsers(query, sort, page, perPage) }.returns(
            Response.error(500, errorMessage.toResponseBody()))

        //Invokes
        val result = mGithubRemoteDataSourceImpl.searchUsers(query, sort, page, perPage).first()

        //Then
        coVerify(exactly = 1) {  mGithubApiService.searchUsers(query, sort, page, perPage) }
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Error::class.java))
        MatcherAssert.assertThat((result as DataState.Error).message, CoreMatchers.`is`(errorMessage))
    }

    @Test
    fun `test userDetails invokes mGithubApiService_userDetails and returns DataState_success`() = runBlocking {
        //Given
        val userName = Utils.getRandomString()

        val fakeResponse = getUserDetailEntity()

        coEvery { mGithubApiService.userDetails(userName) }.returns(
            Response.success(fakeResponse))

        //Invokes
        val result = mGithubRemoteDataSourceImpl.userDetails(userName).first()

        //Then
        coVerify(exactly = 1) {  mGithubApiService.userDetails(userName) }
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Success::class.java))
        MatcherAssert.assertThat((result as DataState.Success).data, CoreMatchers.`is`(fakeResponse))
    }

    @Test
    fun `test searchUsers invokes mGithubApiService_userDetails and returns DataState_error`() = runBlocking {
        //Given
        val userName = Utils.getRandomString()

        val errorMessage = "Something went wrong"

        every { mNetworkErrorProvider.somethingWentWrong() }.returns(errorMessage)

        coEvery { mGithubApiService.userDetails(userName) }.returns(Response.error(500, errorMessage.toResponseBody()))

        //Invokes
        val result = mGithubRemoteDataSourceImpl.userDetails(userName).first()

        //Then
        coVerify(exactly = 1) {  mGithubApiService.userDetails(userName) }
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Error::class.java))
        MatcherAssert.assertThat((result as DataState.Error).message, CoreMatchers.`is`(errorMessage))
    }

    @After
    fun tearDown() {

    }
}