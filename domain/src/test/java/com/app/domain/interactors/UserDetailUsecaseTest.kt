package com.app.domain.interactors

import com.app.common.DataState
import com.app.common.Utils
import com.app.domain.GithubRepository
import com.app.githubsearch.utils.getFakeUserDetails
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
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
class UserDetailUsecaseTest {
    @MockK
    private lateinit var mGithubRepository : GithubRepository

    private lateinit var mUserDetailUsecase : UserDetailUsecase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mUserDetailUsecase = UserDetailUsecase(mGithubRepository)
    }

    @Test
    fun `test invokes UserDetailUsecaseTest invokes GithubRepository_userDetails and returns DataState_success`() = runBlocking {
        //Given
        val user = getFakeUserDetails()
        val userName = Utils.getRandomString()

        every { mGithubRepository.userDetails(userName) }.returns(flowOf(DataState.success(user)))

        //Then
        val result = mUserDetailUsecase.invoke(userName).first()

        //Verify
        verify(exactly = 1) { mGithubRepository.userDetails(userName) }
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Success::class.java))
        MatcherAssert.assertThat((result as DataState.Success).data, CoreMatchers.`is`(user))
    }
}