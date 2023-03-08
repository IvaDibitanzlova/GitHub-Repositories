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
            val response = client.get(baseURL + "/users/${userName}/repos")
            RepositoryResponse(response.body(), response.status)
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            Log.e("error", ex.response.status.description)
            RepositoryResponse(emptyList(), ex.response.status)
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            Log.e("error", ex.response.status.description)
            RepositoryResponse(emptyList(), ex.response.status)
        } catch (ex: ServerResponseException) {
            // 5xx - responses
            Log.e("error", ex.response.status.description)
            RepositoryResponse(emptyList(), ex.response.status)
        } catch (ex: JsonConvertException) {
            Log.e("error", "message " + ex.message)
            RepositoryResponse(emptyList(), HttpStatusCode(1, "json convert exception"))
        } catch (ex: UnknownHostException) {
            Log.e("error", "message " + ex.message)
            RepositoryResponse(emptyList(), HttpStatusCode(0, "no internet connection"))
        }
    }

    override suspend fun getBranches(userName: String, repositoryName: String): List<Branch> {
        return try {
            client.get(baseURL + "/repos/${userName}/${repositoryName}/branches").body()
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            Log.e("error", ex.response.status.description)
            emptyList()
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            Log.e("error", ex.response.status.description)
            emptyList()
        } catch (ex: ServerResponseException) {
            // 5xx - responses
            Log.e("error", ex.response.status.description)
            emptyList()
        } catch (ex: JsonConvertException) {
            Log.e("error", "message " + ex.message)
            emptyList()
        } catch (ex: UnknownHostException) {
            Log.e("error", "message " + ex.message)
            emptyList()
        }
    }

    override suspend fun getCommits(userName: String, repositoryName: String): CommitResponse {
        return try {
            val response = client.get(baseURL + "/repos/${userName}/${repositoryName}/commits") {
                url {
                    parameters.append("per_page", "10")
                }
            }
            CommitResponse(response.body(), response.status)
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            Log.e("error", ex.response.status.description)
            CommitResponse(emptyList(), ex.response.status)
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            Log.e("error", ex.response.status.description)
            CommitResponse(emptyList(), ex.response.status)
        } catch (ex: ServerResponseException) {
            // 5xx - responses
            Log.e("error", ex.response.status.description)
            CommitResponse(emptyList(), ex.response.status)
        } catch (ex: JsonConvertException) {
            Log.e("error", "message " + ex.message)
            CommitResponse(emptyList(), HttpStatusCode(1, "json convert exception"))
        } catch (ex: UnknownHostException) {
            Log.e("error", "message " + ex.message)
            CommitResponse(emptyList(), HttpStatusCode(0, "no internet connection"))
        }
    }
}