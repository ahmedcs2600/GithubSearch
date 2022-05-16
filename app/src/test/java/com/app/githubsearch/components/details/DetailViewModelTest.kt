package com.app.githubsearch.components.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.common.DataState
import com.app.common.Utils
import com.app.domain.interactors.UserDetailUsecase
import com.app.githubsearch.utils.MainCoroutinesRule
import com.app.githubsearch.utils.captureValues
import com.app.githubsearch.utils.getFakeUserDetails
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK(relaxed = true)
    lateinit var mUserDetailUsecase: UserDetailUsecase

    private lateinit var viewModel : DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = DetailViewModel(mUserDetailUsecase)
    }

    @Test
    fun `test init DetailViewModel dispatch DetailContract_UIEvent_LoadUserDetail invokes UserDetailUsecase and returns DataState_Success`() {
        //Given
        val fakeResult = getFakeUserDetails()
        val userName = Utils.getRandomString()
        every { mUserDetailUsecase.invoke(userName) }.returns(flowOf(DataState.success(fakeResult)))
        val values = viewModel.uiState.captureValues()
        val uiEffectValues = viewModel.uiEffect.captureValues()

        //Invokes
        viewModel.dispatchEvent(DetailContract.UIEvent.LoadUserDetail(userName))

        //Then
        coVerify(exactly = 1) { mUserDetailUsecase.invoke(userName) }
        MatcherAssert.assertThat(values.size, CoreMatchers.`is`(2))
        MatcherAssert.assertThat(uiEffectValues.size, CoreMatchers.`is`(0))
        MatcherAssert.assertThat(values.first(), CoreMatchers.instanceOf(DetailContract.UIState.Loading::class.java))
        MatcherAssert.assertThat(values.last(), CoreMatchers.instanceOf(DetailContract.UIState.UserDetailLoaded::class.java))
        MatcherAssert.assertThat((values.last() as DetailContract.UIState.UserDetailLoaded).data, CoreMatchers.`is`(fakeResult))
    }

    @Test
    fun `test init DetailViewModel dispatch DetailContract_UIEvent_LoadUserDetail invokes UserDetailUsecase and returns DataState_Error`() {
        //Given
        val userName = Utils.getRandomString()
        val errorMessage = "Something went wrong"
        every { mUserDetailUsecase.invoke(userName) }.returns(flowOf(DataState.error(errorMessage)))
        val uiValues = viewModel.uiState.captureValues()
        val uiEffectValues = viewModel.uiEffect.captureValues()

        //Invokes
        viewModel.dispatchEvent(DetailContract.UIEvent.LoadUserDetail(userName))

        //Then
        coVerify(exactly = 1) { mUserDetailUsecase.invoke(userName) }
        MatcherAssert.assertThat(uiValues.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(uiEffectValues.size, CoreMatchers.`is`(1))

        MatcherAssert.assertThat(uiValues.first(), CoreMatchers.instanceOf(DetailContract.UIState.Loading::class.java))
        MatcherAssert.assertThat(uiEffectValues.first()?.peekContent(), CoreMatchers.instanceOf(DetailContract.UIEffect.Error::class.java))
        MatcherAssert.assertThat(((uiEffectValues.first()?.peekContent()) as DetailContract.UIEffect.Error).message, CoreMatchers.`is`(errorMessage))
    }


    @Test
    fun `test init DetailViewModel dispatch DetailContract_UIEvent_OpenBrowser invokes UserDetailUsecase and returns DataState_Error`() {
        //Given
        val url = "google.com"

        val uiEffectValues = viewModel.uiEffect.captureValues()

        //Invokes
        viewModel.dispatchEvent(DetailContract.UIEvent.OpenBrowser(url))

        //Then
        MatcherAssert.assertThat(uiEffectValues.size, CoreMatchers.`is`(1))

        MatcherAssert.assertThat(uiEffectValues.first()?.peekContent(), CoreMatchers.instanceOf(DetailContract.UIEffect.OpenBrowser::class.java))
        MatcherAssert.assertThat(((uiEffectValues.first()?.peekContent()) as DetailContract.UIEffect.OpenBrowser).url, CoreMatchers.`is`(url))
    }

    @After
    fun tearDown() {

    }
}