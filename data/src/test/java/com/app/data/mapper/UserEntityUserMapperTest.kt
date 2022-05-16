package com.app.data.mapper

import com.app.common.Utils
import com.app.domain.models.User
import com.app.network.model.UserEntity
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserEntityUserMapperTest {

    private lateinit var mUserEntityUserMapper : UserEntityUserMapper

    @Before
    fun setUp() {
        mUserEntityUserMapper = UserEntityUserMapper()
    }

    @Test
    fun `test UserEntityUserMapper mapTo returns User`() {
        val item = UserEntity(
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

        val user =  User(
            id = item.id,
            login = item.login,
            type = item.type,
            avatarUrl = item.avatarUrl,
            score = item.score,
        )

        mUserEntityUserMapper.mapTo(item)

        MatcherAssert.assertThat(user, CoreMatchers.`is`(user))
    }
}