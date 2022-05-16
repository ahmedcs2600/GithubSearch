package com.app.common

import java.util.*

object Utils {
    fun getRandomId() = UUID.randomUUID().toString()

    fun getRandomInt() = Random().nextInt()

    fun getRandomLong() = Random().nextLong()

    fun getRandomDouble() = Random().nextDouble()

    fun getRandomString() = UUID.randomUUID().toString()

    fun getRandomBoolean() = Random().nextBoolean()
}