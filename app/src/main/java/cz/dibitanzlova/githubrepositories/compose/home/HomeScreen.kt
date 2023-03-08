package cz.dibitanzlova.githubrepositories.compose.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.dibitanzlova.githubrepositories.R
import cz.dibitanzlova.githubrepositories.viewmodels.HomeViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.dibitanzlova.githubrepositories.model.DetailRepository
import cz.dibitanzlova.githubrepositories.model.HomeState
import cz.dibitanzlova.githubrepositories.model.Repository

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onRepositoryClick: (DetailRepository) -> Unit = {},
    onAboutClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    HomeScreenMainComposable(state,
        modifier,
        onRepositoryClick,
        onAboutClick,
        { query -> viewModel.onQueryChange(query) },
        { query -> viewModel.onSearch(query) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMainComposable(
    state: HomeState,
    modifier: Modifier,
    onRepositoryClick: (DetailRepository) -> Unit = {},
    onAboutClick: () -> Unit = {},
    onQueryChangeCallback: (query: String) -> Unit = {},
    onSearchCallback: (query: String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                modifier = modifier,
                actions = {
                    IconButton(onClick = { onAboutClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(id = R.string.button_info_description)
                        )
                    }
                }

            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                SearchBar(
                    query = state.query,
                    onQueryChange = { query -> onQueryChangeCallback(query) },
                    onSearch = { query -> onSearchCallback(query) },
                    active = true,
                    onActiveChange = {},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.icon_search_description)
                        )
                    },
                    placeholder = {
                        Text(
                            stringResource(id = R.string.search_placeholder)
                        )
                    },
                    tonalElevation = 0.dp
                ) {
                    if (state.isProgressShown) {
                        CircularProgressIndicator(
                            modifier = modifier
                                .align(CenterHorizontally)
                                .padding(top = 20.dp)
                        )
                    } else {
                        if (state.showNoUserFound) {
                            Text(
                                stringResource(id = R.string.user_not_found),
                                Modifier
                                    .align(CenterHorizontally)
                                    .padding(20.dp)
                            )
                        } else if (state.showNoRepositoriesFound) {
                            Text(
                                stringResource(id = R.string.no_repositories_found),
                                Modifier
                                    .align(CenterHorizontally)
                                    .padding(20.dp)
                            )
                        } else if (state.showNoConnection) {
                            Text(
                                stringResource(id = R.string.no_connection),
                                Modifier
                                    .align(CenterHorizontally)
                                    .padding(20.dp)
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 20.dp)
                            ) {
                                items(items = state.repositories, itemContent = { item ->
                                    Column {
                                        ListItem(
                                            headlineText = { Text(item.name) },
                                            supportingText = { Text(item.description) },
                                            modifier = modifier.clickable {
                                                onRepositoryClick(
                                                    DetailRepository(state.query, item.name)
                                                )
                                            }
                                        )
                                        Divider()
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenMainComposable(
        state = HomeState(
            "someName",
            List(4) { i -> Repository("repository $i", "description $i") },
            showNoUserFound = false,
            showNoRepositoriesFound = false
        ),
        modifier = Modifier
    )
}