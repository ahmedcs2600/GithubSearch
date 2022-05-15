package com.app.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.app.domain.models.User

interface GithubRepository {

    fun searchUsers(keyword : String): LiveData<PagingData<User>>
}