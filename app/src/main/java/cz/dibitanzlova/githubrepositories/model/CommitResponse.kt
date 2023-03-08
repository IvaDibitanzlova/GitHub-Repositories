package cz.dibitanzlova.githubrepositories.model

import io.ktor.http.*

data class CommitResponse(
    val listCommits: List<CommitWrapper>,
    val httpStatusCode: HttpStatusCode
)