package com.app.common

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class DataStateTest {

    @Test
    fun `test invoke DataState_success() returns Success type`() {
        //Given
        val data = Pair(1,2)

        //Invoke
        val result = DataState.success(data)

        //verify
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Success::class.java))
        MatcherAssert.assertThat(result.data, CoreMatchers.`is`(data))
    }

    @Test
    fun `test invoke DataState_error() returns Error type`() {
        //Given
        val errorMessage = "Something went wrong"

        //Invoke
        val result = DataState.error<Any>(errorMessage)

        //verify
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Error::class.java))
        MatcherAssert.assertThat(result.message, CoreMatchers.`is`(errorMessage))
    }
}