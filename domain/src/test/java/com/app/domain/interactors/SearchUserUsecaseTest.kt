package com.app.domain.interactors

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.app.common.Utils
import com.app.domain.GithubRepository
import com.app.domain.utils.getFakeUserData
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchUserUsecaseTest {

    @MockK
    private lateinit var mGithubRepository : GithubRepository

    private lateinit var mSearchUserUsecase : SearchUserUsecase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mSearchUserUsecase = SearchUserUsecase(mGithubRepository)
    }

    @Test
    fun `test invokes SearchUserUsecase invokes GithubRepository_searchUsers and returns PagingData_Users`() {
        //Given
        val users = (1..5).map {  getFakeUserData()  }
        val pagingData = PagingData.from(users)
        val keyword = Utils.getRandomString()

        every { mGithubRepository.searchUsers(keyword) }.returns(MutableLiveData(pagingData))

        //Then
        val result = mSearchUserUsecase.invoke(keyword)

        //Verify
        verify(exactly = 1) { mGithubRepository.searchUsers(keyword) }
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result.value, CoreMatchers.notNullValue())
    }
}