package com.app.network.interceptors

import com.app.common.AUTHORIZATION_KEY
import com.app.common.Secrets
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptors @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().newBuilder().apply {
            addHeader(AUTHORIZATION_KEY, Secrets.githubToken())
        }.build())
    }
}