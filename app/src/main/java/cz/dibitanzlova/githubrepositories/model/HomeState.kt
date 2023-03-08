package cz.dibitanzlova.githubrepositories.model

data class HomeState(
    val query: String,
    val repositories: List<Repository>,
    val showNoUserFound: Boolean = false,
    val showNoRepositoriesFound: Boolean = false,
)