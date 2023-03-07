package cz.dibitanzlova.githubrepositories.network

import cz.dibitanzlova.githubrepositories.model.Repository
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

    suspend fun getRepositories(userName: String): List<Repository>

    companion object {
        fun create(): GitHubRemoteDataSource {
            return GitHubRemoteDataSourceImpl(
                client = HttpClient(Android) {
                    // Logging
                    install(Logging) {
                        level = LogLevel.ALL
                    }
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
                        // Parameter("api_key", "some_api_key")
                        // Content Type
                        //if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }
                }
            )
        }
    }
}