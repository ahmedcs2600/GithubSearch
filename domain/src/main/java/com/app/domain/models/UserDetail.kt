package com.app.domain.models

data class UserDetail(
    val login: String,
    val id: Int,
    val avatarUrl: String?,
    val url: String?,
    val company: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val htmlUrl : String?,
    val followingUrl : String?,
    val followersUrl : String?,
    val reposUrl : String?,
    val eventsUrl : String?,
)