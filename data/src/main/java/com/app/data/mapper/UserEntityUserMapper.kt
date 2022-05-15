package com.app.data.mapper

import com.app.common.Mapper
import com.app.domain.models.User
import com.app.network.model.UserEntity
import javax.inject.Inject

class UserEntityUserMapper @Inject constructor(): Mapper<UserEntity, User> {
    override fun mapTo(item: UserEntity): User {
        return User(id = item.id)
    }
}