package cz.dibitanzlova.githubrepositories.network

import android.util.Log
import cz.dibitanzlova.githubrepositories.BuildConfig
import cz.dibitanzlova.githubrepositories.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.*
import java.net.UnknownHostException

class GitHubRemoteDataSourceImpl(
    private val client: HttpClient
) : GitHubRemoteDataSource {

    private val baseURL = BuildConfig.API_BASE_URL

    override suspend fun getRepositories(userName: String): RepositoryResponse {
        return try {
            val response = client.get("${baseURL}/users/${userName}/repos")
            RepositoryResponse(response.body(), response.status)
        } catch (ex: Exception) {
            when (ex) {
                is ServerResponseException -> getRepositoriesError(
                    ex.response.status.description,
                    ex.response.status
                )
                is JsonConvertException -> getRepositoriesError(
                    ex.message.toString(),
                    HttpStatusCode(1, "json convert exception")
                )
                is UnknownHostException -> getRepositoriesError(
                    ex.message.toString(),
                    HttpStatusCode(0, "no internet connection")
                )
                else -> {
                    getRepositoriesError(ex.message.toString(), HttpStatusCode(100, "fetch repositories error"))
                }
            }
        }
    }

    override suspend fun getBranches(userName: String, repositoryName: String): List<Branch> {
        return try {
            client.get("${baseURL}/repos/${userName}/${repositoryName}/branches").body()
        } catch (ex: Exception) {
            when (ex) {
                is ServerResponseException -> getBranchesError(
                    ex.response.status.description
                )
                is JsonConvertException -> getBranchesError(
                    ex.message.toString()
                )
                is UnknownHostException -> getBranchesError(
                    ex.message.toString()
                )
                else -> {
                    getBranchesError(ex.message.toString())
                }
            }
        }
    }

    override suspend fun getCommits(userName: String, repositoryName: String): CommitResponse {
        return try {
            val response = client.get("${baseURL}/repos/${userName}/${repositoryName}/commits") {
                url {
                    parameters.append("per_page", "10")
                }
            }
            CommitResponse(response.body(), response.status)
        } catch (ex: Exception) {
            when (ex) {
                is ServerResponseException -> getCommitsError(
                    ex.response.status.description,
                    ex.response.status
                )
                is JsonConvertException -> getCommitsError(
                    ex.message.toString(),
                    HttpStatusCode(1, "json convert exception")
                )
                is UnknownHostException -> getCommitsError(
                    ex.message.toString(),
                    HttpStatusCode(0, "no internet connection")
                )
                else -> {
                    getCommitsError(ex.message.toString(), HttpStatusCode(100, "fetch repositories error"))
                }
            }
        }
    }

    private fun getRepositoriesError(
        description: String,
        status: HttpStatusCode
    ): RepositoryResponse {
        Log.e("error", "message $description")
        return RepositoryResponse(emptyList(), status)
    }

    private fun getBranchesError(
        description: String
    ): List<Branch> {
        Log.e("error", "message $description")
        return emptyList()
    }

    private fun getCommitsError(
        description: String,
        status: HttpStatusCode
    ): CommitResponse {
        Log.e("error", "message $description")
        return CommitResponse(emptyList(), status)
    }
}