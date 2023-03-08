package cz.dibitanzlova.githubrepositories.model

@kotlinx.serialization.Serializable
data class CommitResponse(
    val commit: Commit
)

@kotlinx.serialization.Serializable
data class Commit(
    val message: String,
    val author: Author,
)

@kotlinx.serialization.Serializable
data class Author(
    val name: String,
    val date: String,
)