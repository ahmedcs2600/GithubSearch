package com.app.data.mapper

import com.app.common.Mapper
import com.app.domain.models.UserDetail
import com.app.network.model.UserDetailEntity
import javax.inject.Inject

class UserDetailEntityUserDetailMapper @Inject constructor(): Mapper<UserDetailEntity, UserDetail> {
    override fun mapTo(item: UserDetailEntity): UserDetail {
        return UserDetail(
            login = item.login,
            id = item.id,
            avatarUrl = item.avatarUrl,
            url = item.url,
            company = item.company,
            location = item.location,
            email = item.email,
            bio = item.bio,
            htmlUrl = item.htmlUrl,
            followingUrl = item.followingUrl,
            followersUrl = item.followersUrl,
            reposUrl = item.reposUrl,
            eventsUrl = item.eventsUrl,
        )
    }
}