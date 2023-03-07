package cz.dibitanzlova.githubrepositories.di

import cz.dibitanzlova.githubrepositories.network.GitHubRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object GitHubRepositoriesModule {
    @Provides
    fun provideGitHubRemoteDataSource(): GitHubRemoteDataSource {
        return GitHubRemoteDataSource.create()
    }
}