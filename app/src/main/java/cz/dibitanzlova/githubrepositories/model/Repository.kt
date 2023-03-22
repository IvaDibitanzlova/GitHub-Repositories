package cz.dibitanzlova.githubrepositories.model

@kotlinx.serialization.Serializable
data class Repository(
    val name: String,
    val description: String?
)