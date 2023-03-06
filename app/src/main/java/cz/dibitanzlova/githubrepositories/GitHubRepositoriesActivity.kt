package cz.dibitanzlova.githubrepositories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cz.dibitanzlova.githubrepositories.compose.GitHubRepositoriesApp
import cz.dibitanzlova.githubrepositories.ui.theme.GitHubRepositoriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitHubRepositoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubRepositoriesTheme {
                GitHubRepositoriesApp(
                )
            }
        }
    }
}