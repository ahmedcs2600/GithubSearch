package com.app.domain.models

data class User(
    val id : Int,
    val login : String,
    val type : String?,
    val avatarUrl : String?,
    val score : Int,
)