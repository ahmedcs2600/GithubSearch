package com.app.githubsearch.utils

import com.app.common.Utils
import com.app.domain.models.User

fun getFakeUserData(): User {
    return User(
        id = Utils.getRandomInt(),
        login = Utils.getRandomString(),
        type = Utils.getRandomString(),
        avatarUrl = Utils.getRandomString(),
        score = Utils.getRandomInt(),
    )
}