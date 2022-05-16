package com.app.data.utils

import com.app.common.Utils
import com.app.network.model.SearchUserResponse
import com.app.network.model.UserEntity

fun getSearchUserFakeResponse() : SearchUserResponse {
    return SearchUserResponse(
        totalCount = Utils.getRandomInt(),
        incompleteResults = Utils.getRandomBoolean(),
        items = (1..5).map { getFakeUserEntity() })
}

fun getFakeUserEntity(): UserEntity {
    return UserEntity(
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
        score = Utils.getRandomInt(),
        followingUrl = Utils.getRandomString(),
        gistsUrl = Utils.getRandomString(),
        starredUrl = Utils.getRandomString(),
        eventsUrl = Utils.getRandomString(),
        siteAdmin = Utils.getRandomBoolean(),
    )
}