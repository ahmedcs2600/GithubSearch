package com.app.githubsearch.di

import com.app.domain.GithubRepository
import com.app.githubsearch.fakes.FakeGithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TestRepositoryModule {
    @Provides
    fun providesGithubRepository(): GithubRepository {
        return FakeGithubRepository()
    }
}