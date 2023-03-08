package cz.dibitanzlova.githubrepositories.compose.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.dibitanzlova.githubrepositories.R
import cz.dibitanzlova.githubrepositories.model.*
import cz.dibitanzlova.githubrepositories.viewmodels.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    modifier: Modifier = Modifier,
    onAboutClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    DetailScreenMainComposable(
        state,
        modifier,
        onBackClick,
        onAboutClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenMainComposable(
    state: DetailState,
    modifier: Modifier,
    onBackClick: () -> Unit = {},
    onAboutClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.repositoryName) },
                modifier = modifier,
                actions = {
                    IconButton(onClick = { onAboutClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(id = R.string.button_info_description)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    text = stringResource(R.string.title_branches),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = modifier.padding(15.dp)
                )
                LazyColumn(modifier = Modifier.padding(bottom = 20.dp)) {
                    items(items = state.branches, itemContent = { item ->
                        Column {
                            ListItem(
                                headlineText = { Text(item.name) }
                            )
                            Divider()
                        }
                    })
                }
                Text(
                    text = stringResource(R.string.title_commits),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = modifier.padding(15.dp)
                )
                LazyColumn(modifier = modifier) {
                    items(items = state.commits, itemContent = { item ->
                        Column {
                            ListItem(
                                headlineText = { Text(item.commit.message) },
                                supportingText = {
                                    Row(modifier = modifier) {
                                        Text(item.commit.author.date)
                                        Text(item.commit.author.name)
                                    }
                                }
                            )
                            Divider()
                        }
                    })
                }
            }
        }
    )
}

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreenMainComposable(
        state = DetailState(
            "someName",
            List(2) { i -> Branch("branch $i") },
            List(10) { i -> CommitResponse(Commit("commit $i", Author("autor", "1. 1. 2000"))) },
        ),
        modifier = Modifier
    )
}