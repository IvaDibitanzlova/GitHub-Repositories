package cz.dibitanzlova.githubrepositories.network

import cz.dibitanzlova.githubrepositories.model.Branch
import cz.dibitanzlova.githubrepositories.model.CommitResponse
import cz.dibitanzlova.githubrepositories.model.RepositoryResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface GitHubRemoteDataSource {

    suspend fun getRepositories(userName: String): RepositoryResponse
    suspend fun getBranches(userName: String, repositoryName: String): List<Branch>
    suspend fun getCommits(userName: String, repositoryName: String): CommitResponse

    companion object {
        fun create(): GitHubRemoteDataSource {
            return GitHubRemoteDataSourceImpl(
                client = HttpClient(Android) {
                    expectSuccess = true
                    // Logging
                    install(Logging) {
                        logger = HttpLogger()
                        level = LogLevel.ALL
                    }
                    // Serialization
                    install(ContentNegotiation) {
                        json(Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                            encodeDefaults = false
                        })
                    }
                    // Timeout
                    install(HttpTimeout) {
                        requestTimeoutMillis = 15000L
                        connectTimeoutMillis = 15000L
                        socketTimeoutMillis = 15000L
                    }
                    // Apply to all requests
                    defaultRequest {
                        accept(ContentType.Application.Json)
                    }
                }
            )
        }
    }
}