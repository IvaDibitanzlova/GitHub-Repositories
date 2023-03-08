package cz.dibitanzlova.githubrepositories.model

import io.ktor.http.*

data class RepositoryResponse(
    val listRepositories: List<Repository>,
    val httpStatusCode: HttpStatusCode
)