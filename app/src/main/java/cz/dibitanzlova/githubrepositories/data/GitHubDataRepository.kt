package cz.dibitanzlova.githubrepositories.data

import cz.dibitanzlova.githubrepositories.model.Repository
import cz.dibitanzlova.githubrepositories.network.GitHubRemoteDataSource
import javax.inject.Inject

class GitHubDataRepository @Inject constructor(
    private val gitHubRemoteDataSource: GitHubRemoteDataSource
) {
    suspend fun getRepositories(userName: String): List<Repository> {
        return gitHubRemoteDataSource.getRepositories(userName)
    }
}