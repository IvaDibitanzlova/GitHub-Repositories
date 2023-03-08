package cz.dibitanzlova.githubrepositories.data

import cz.dibitanzlova.githubrepositories.model.Branch
import cz.dibitanzlova.githubrepositories.model.Commit
import cz.dibitanzlova.githubrepositories.model.CommitResponse
import cz.dibitanzlova.githubrepositories.model.RepositoryResponse
import cz.dibitanzlova.githubrepositories.network.GitHubRemoteDataSource
import javax.inject.Inject

class GitHubDataRepository @Inject constructor(
    private val gitHubRemoteDataSource: GitHubRemoteDataSource
) {
    suspend fun getRepositories(userName: String): RepositoryResponse {
        return gitHubRemoteDataSource.getRepositories(userName)
    }

    suspend fun getBranches(userName: String, repositoryName: String): List<Branch> {
        return gitHubRemoteDataSource.getBranches(userName, repositoryName)
    }

    suspend fun getCommits(userName: String, repositoryName: String): List<CommitResponse> {
        return gitHubRemoteDataSource.getCommits(userName, repositoryName)
    }
}