package com.app.data

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface UserSearchPagingFactory {
    fun createUserSearchPaging(@Assisted("keyword") keyword: String): UserSearchPagingSource
}