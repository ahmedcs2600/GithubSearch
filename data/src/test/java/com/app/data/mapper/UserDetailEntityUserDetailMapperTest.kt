package com.app.data.mapper

import com.app.common.Utils
import com.app.domain.models.UserDetail
import com.app.network.model.UserDetailEntity
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDetailEntityUserDetailMapperTest {

    private lateinit var mUserDetailEntityUserDetailMapper : UserDetailEntityUserDetailMapper

    @Before
    fun setUp() {
        mUserDetailEntityUserDetailMapper = UserDetailEntityUserDetailMapper()
    }

    @Test
    fun `test UserDetailEntityUserDetailMapper mapTo returns UserDetail`() {
        val item = UserDetailEntity(
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

        val userDetail = UserDetail(
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

        val result = mUserDetailEntityUserDetailMapper.mapTo(item)
        MatcherAssert.assertThat(userDetail, CoreMatchers.`is`(userDetail))
    }
}