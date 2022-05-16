package com.app.githubsearch.utils

import com.app.common.Utils
import com.app.domain.models.UserDetail

fun getFakeUserDetails(): UserDetail {
    return UserDetail(
        login = Utils.getRandomString(),
        id = Utils.getRandomInt(),
        avatarUrl = Utils.getRandomString(),
        url = Utils.getRandomString(),
        company = Utils.getRandomString(),
        location = Utils.getRandomString(),
        email = Utils.getRandomString(),
        bio = Utils.getRandomString(),
        htmlUrl = Utils.getRandomString(),
        followingUrl = Utils.getRandomString(),
        followersUrl = Utils.getRandomString(),
        reposUrl = Utils.getRandomString(),
        eventsUrl = Utils.getRandomString(),
    )
}