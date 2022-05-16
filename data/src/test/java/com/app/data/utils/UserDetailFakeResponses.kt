package com.app.data.utils

import com.app.common.Utils
import com.app.network.model.UserDetailEntity

fun getUserDetailEntity(): UserDetailEntity {
    return UserDetailEntity(
        login = Utils.getRandomString(),
        id = Utils.getRandomInt(),
        nodeId = Utils.getRandomString(),
        avatarUrl = Utils.getRandomString(),
        gravatarId = Utils.getRandomString(),
        url = Utils.getRandomString(),
        htmlUrl = Utils.getRandomString(),
        followersUrl = Utils.getRandomString(),
        subscriptionsUrl = Utils.getRandomString(),
        organizationsUrl = Utils.getRandomString(),
        reposUrl = Utils.getRandomString(),
        receivedEventsUrl = Utils.getRandomString(),
        type = Utils.getRandomString(),
        followingUrl = Utils.getRandomString(),
        gistsUrl = Utils.getRandomString(),
        starredUrl = Utils.getRandomString(),
        eventsUrl = Utils.getRandomString(),
        siteAdmin = Utils.getRandomBoolean(),
        bio = Utils.getRandomString(),
        blog = Utils.getRandomString(),
        company = Utils.getRandomString(),
        createdAt = Utils.getRandomString(),
        email = Utils.getRandomString(),
        followers = Utils.getRandomInt(),
        following = Utils.getRandomInt(),
        hireable = Utils.getRandomBoolean(),
        location = Utils.getRandomString(),
        name = Utils.getRandomString(),
        publicGists = Utils.getRandomInt(),
        publicRepos = Utils.getRandomInt(),
        twitterUsername = Utils.getRandomString(),
        updatedAt = Utils.getRandomString(),
    )
}