package cz.dibitanzlova.githubrepositories.compose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.dibitanzlova.githubrepositories.compose.about.AboutScreen
import cz.dibitanzlova.githubrepositories.compose.detail.DetailScreen
import cz.dibitanzlova.githubrepositories.compose.home.HomeScreen

@Composable
fun GitHubRepositoriesApp() {
    val navController = rememberNavController()
    GitHubRepositoriesNavHost(
        navController = navController
    )
}

@Composable
fun GitHubRepositoriesNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = hiltViewModel(),
                onUserClick = {
                    navController.navigate("detail/${it.id}")
                },
                onAboutClick = {
                    navController.navigate("about")
                }
            )
        }
        composable(
            "detail/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
            })
        ) {
            DetailScreen(
                viewModel = hiltViewModel(),
                onBackClick = { navController.navigateUp() },
                onAboutClick = {
                    navController.navigate("about")
                }
            )
        }
        composable("about") {
            AboutScreen(
                viewModel = hiltViewModel(),
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}