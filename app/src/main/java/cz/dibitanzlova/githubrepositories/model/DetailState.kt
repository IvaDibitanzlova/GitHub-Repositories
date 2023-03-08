package cz.dibitanzlova.githubrepositories.model

data class DetailState(
    val repositoryName: String,
    val branches: List<Branch>,
    val commits: List<CommitResponse >,
)