package cz.dibitanzlova.githubrepositories.compose.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.dibitanzlova.githubrepositories.R
import cz.dibitanzlova.githubrepositories.model.User
import cz.dibitanzlova.githubrepositories.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onUserClick: (User) -> Unit = {},
    onAboutClick: () -> Unit = {}
) {
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
}