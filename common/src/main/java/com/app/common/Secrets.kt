package com.app.common

object Secrets {
    init {
        System.loadLibrary("native-lib")
    }
    external fun githubToken() : String
}