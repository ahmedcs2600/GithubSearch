package com.app.common

interface Mapper<A, B> {
    fun mapTo(item : A) : B
}