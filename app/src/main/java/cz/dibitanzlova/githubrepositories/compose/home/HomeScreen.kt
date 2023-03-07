package cz.dibitanzlova.githubrepositories.compose.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import cz.dibitanzlova.githubrepositories.R
import cz.dibitanzlova.githubrepositories.viewmodels.HomeViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.dibitanzlova.githubrepositories.model.Repository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onRepositoryClick: (Repository) -> Unit = {},
    onAboutClick: () -> Unit = {}
) {
    // val status by viewModel.status.collectAsStateWithLifecycle()

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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                /*Text(
                    text = "Status: " + status, // test
                    fontWeight = FontWeight.Bold,
                )*/
            }
        }
    )
}