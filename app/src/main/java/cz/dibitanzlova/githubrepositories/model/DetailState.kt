package cz.dibitanzlova.githubrepositories.model

data class DetailState(
    val repositoryName: String,
    val branches: List<Branch>,
    val commits: List<CommitWrapper >,
    val isBranchesProgressShown: Boolean = true,
    val isCommitsProgressShown: Boolean = true,
    val showNoConnection: Boolean = false,
)