package cz.dibitanzlova.githubrepositories.network

import cz.dibitanzlova.githubrepositories.BuildConfig
import cz.dibitanzlova.githubrepositories.model.Repository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*

class GitHubRemoteDataSourceImpl(
    private val client: HttpClient
) : GitHubRemoteDataSource {

    val baseURL = BuildConfig.API_BASE_URL

    override suspend fun getRepositories(userName: String): List<Repository> {
        return try {
            client.get(baseURL + "/users/${userName}/repos").body()
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ServerResponseException) {
            // 5xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        }
    }
}