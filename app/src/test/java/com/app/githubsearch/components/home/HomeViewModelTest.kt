package com.app.githubsearch.components.home

import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.app.domain.interactors.SearchUserUsecase
import com.app.githubsearch.utils.MainCoroutinesRule
import com.app.githubsearch.utils.getFakeUserData
import com.app.githubsearch.utils.Event
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var mSearchUserUsecase : SearchUserUsecase

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test creating HomeViewModel invokes SearchUserUsecase and returns HomeContract_UIState_SearchedResult`() {
        //Given
        val observer = mockk<Observer<HomeContract.UIState>>(relaxed = true)

        val users = (1..5).map {  getFakeUserData()  }
        val pagingData = PagingData.from(users)
        coEvery { mSearchUserUsecase.invoke(any()) }.returns(MutableLiveData(pagingData))

        //Invokes
        val mHomeViewModel = HomeViewModel(mSearchUserUsecase)
        mHomeViewModel.uiState.observeForever(observer)
        //Then
        verify(exactly = 1) { mSearchUserUsecase.invoke(any()) }

        verify {
            observer.onChanged(match {
                it is HomeContract.UIState.SearchedResult
            })
        }
    }

    @Test
    fun `test dispatch Search Event changes currentQuery`() {
        //Given
        val query = "Ahmed"
        val observer = mockk<Observer<String>>(relaxed = true)

        //Invokes
        val mHomeViewModel = HomeViewModel(mSearchUserUsecase)
        mHomeViewModel.currentQuery.observeForever(observer)

        //Then
        mHomeViewModel.dispatchEvent(HomeContract.UIEvent.Search(query))

        verify {
            observer.onChanged(match {
                it == query
            })
        }
    }

    @Test
    fun `test dispatch Search Event with empty query set HomeContract_UIEffect_SearchedQueryEmpty`() {
        //Given
        val query = ""
        val observer = mockk<Observer<String>>(relaxed = true)
        val effectObserver = mockk<Observer<Event<HomeContract.UIEffect>>>(relaxed = true)

        //Invokes
        val mHomeViewModel = HomeViewModel(mSearchUserUsecase)
        mHomeViewModel.currentQuery.observeForever(observer)
        mHomeViewModel.uiEffect.observeForever(effectObserver)

        //Then
        mHomeViewModel.dispatchEvent(HomeContract.UIEvent.Search(query))

        verify {
            observer.onChanged(match {
                it == HomeViewModel.INITIAL_QUERY
            })
        }

        verify {
            effectObserver.onChanged(match {
                it.peekContent() == HomeContract.UIEffect.SearchedQueryEmpty
            })
        }
    }


    @Test
    fun `test dispatch NavigateToDetails Event set HomeContract_UIEffect_NavigateToDetails`() {
        //Given
        val userName = "Ahmed"
        val effectObserver = mockk<Observer<Event<HomeContract.UIEffect>>>(relaxed = true)

        //Invokes
        val mHomeViewModel = HomeViewModel(mSearchUserUsecase)
        mHomeViewModel.uiEffect.observeForever(effectObserver)

        //Then
        mHomeViewModel.dispatchEvent(HomeContract.UIEvent.NavigateToDetails(userName))

        verify {
            effectObserver.onChanged(match {
                it.peekContent() == HomeContract.UIEffect.NavigateToDetails(userName)
            })
        }
    }

    @Test
    fun `test dispatch Retry Event set HomeContract_UIEffect_Retry`() {
        //Given
        val userName = "Ahmed"
        val effectObserver = mockk<Observer<Event<HomeContract.UIEffect>>>(relaxed = true)

        //Invokes
        val mHomeViewModel = HomeViewModel(mSearchUserUsecase)
        mHomeViewModel.uiEffect.observeForever(effectObserver)

        //Then
        mHomeViewModel.dispatchEvent(HomeContract.UIEvent.Retry)

        verify {
            effectObserver.onChanged(match {
                it.peekContent() == HomeContract.UIEffect.Retry
            })
        }
    }

    @After
    fun tearDown() {

    }
}