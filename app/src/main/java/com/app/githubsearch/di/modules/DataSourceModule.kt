package com.app.githubsearch.di.modules

import com.app.network.datasource.GithubRemoteDataSource
import com.app.network.datasource.GithubRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindGithubRemoteDataSource(local: GithubRemoteDataSourceImpl): GithubRemoteDataSource
}

