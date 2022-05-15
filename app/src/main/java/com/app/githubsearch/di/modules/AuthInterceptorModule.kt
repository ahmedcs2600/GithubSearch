package com.app.githubsearch.di.modules

import com.app.network.interceptors.AuthInterceptors
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AuthInterceptor

@Module
@InstallIn(SingletonComponent::class)
interface AuthInterceptorModule {

    @AuthInterceptor
    @Binds
    @Singleton
    fun providesLoggingInterceptor(interceptor : AuthInterceptors): Interceptor
}