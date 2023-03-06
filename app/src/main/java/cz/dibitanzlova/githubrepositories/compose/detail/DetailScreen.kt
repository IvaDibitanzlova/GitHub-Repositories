package cz.dibitanzlova.githubrepositories.compose.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.dibitanzlova.githubrepositories.R
import cz.dibitanzlova.githubrepositories.viewmodels.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    modifier: Modifier = Modifier,
    onAboutClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "TODO") },
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
}