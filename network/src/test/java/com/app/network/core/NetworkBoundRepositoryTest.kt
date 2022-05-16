package com.app.network.core

import com.app.common.API_RATE_LIMIT_ERROR
import com.app.common.DataState
import com.app.common.Utils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import java.io.IOException

@RunWith(JUnit4::class)
class NetworkBoundRepositoryTest {

    @MockK
    lateinit var mNetworkErrorProvider : NetworkErrorProvider

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invokes NetworkBoundRepository returns DataState_Success`() = runBlocking {
        //Given
        val responseResult = Utils.getRandomId()

        //Invokes
        val result = object : NetworkBoundRepository<String>(mNetworkErrorProvider) {
            override suspend fun fetchFromRemote(): Response<String> {
                return Response.success(responseResult)
            }
        }.asFlow()

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result.first(), CoreMatchers.instanceOf(DataState.Success::class.java))
        MatcherAssert.assertThat((result.first() as DataState.Success).data, CoreMatchers.instanceOf(String::class.java))
        MatcherAssert.assertThat((result.first() as DataState.Success).data, CoreMatchers.`is`(responseResult))
    }

    @Test
    fun `test invokes NetworkBoundRepository returns DataState_Error`() = runBlocking {
        //Given
        val errorMessage = "Something went wrong"

        every { mNetworkErrorProvider.somethingWentWrong() }.returns(errorMessage)


        //Invokes
        val result = object : NetworkBoundRepository<String>(mNetworkErrorProvider) {
            override suspend fun fetchFromRemote(): Response<String> {
                val body = errorMessage.toResponseBody("text/html".toMediaTypeOrNull())
                return Response.error(500, body)
            }
        }.asFlow()

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result.first(), CoreMatchers.instanceOf(DataState.Error::class.java))
        MatcherAssert.assertThat((result.first() as DataState.Error).message, CoreMatchers.`is`(errorMessage))
    }


    @Test
    fun `test invokes NetworkBoundRepository returns DataState_Error when exception occurs`() = runBlocking {
        //Given
        val errorMessage = "No Internet"
        every { mNetworkErrorProvider.internetConnectionMessage() }.returns(errorMessage)


        //Invokes
        val result = object : NetworkBoundRepository<String>(mNetworkErrorProvider) {
            override suspend fun fetchFromRemote(): Response<String> {
                throw IOException("test test")
            }
        }.asFlow()

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result.first(), CoreMatchers.instanceOf(DataState.Error::class.java))
        MatcherAssert.assertThat((result.first() as DataState.Error).message, CoreMatchers.`is`(errorMessage))
    }

    @Test
    fun `test invokes NetworkBoundRepository returns DataState_Error when API Rate Limiting Occurs`() = runBlocking {
        //Given
        val errorMessage = "Could not get the response"
        every { mNetworkErrorProvider.unableToGetTheResponse() }.returns(errorMessage)


        //Invokes
        val result = object : NetworkBoundRepository<String>(mNetworkErrorProvider) {
            override suspend fun fetchFromRemote(): Response<String> {
                val body = errorMessage.toResponseBody("text/html".toMediaTypeOrNull())
                return Response.error(API_RATE_LIMIT_ERROR, body)
            }
        }.asFlow()

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result.first(), CoreMatchers.instanceOf(DataState.Error::class.java))
        MatcherAssert.assertThat((result.first() as DataState.Error).message, CoreMatchers.`is`(errorMessage))
    }
}