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

class GitHubRemoteDataSourceImpl(
    private val client: HttpClient
) : GitHubRemoteDataSource {

    private val baseURL = BuildConfig.API_BASE_URL

    override suspend fun getRepositories(userName: String): RepositoryResponse {
        return try {
            val call = client.get(baseURL + "/users/${userName}/repos")
            RepositoryResponse(call.body(), call.status)
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
            RepositoryResponse(emptyList(), HttpStatusCode(0, "json convert exception"))
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
        }
    }

    override suspend fun getCommits(userName: String, repositoryName: String): List<CommitResponse> {
        return try {
            client.get(baseURL + "/repos/${userName}/${repositoryName}/commits") {
            url {
                parameters.append("per_page", "10")
            }
        }.body()
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
        }
    }
}