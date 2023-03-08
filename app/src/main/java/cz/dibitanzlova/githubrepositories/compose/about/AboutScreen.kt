package cz.dibitanzlova.githubrepositories.compose.about

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.dibitanzlova.githubrepositories.R
import cz.dibitanzlova.githubrepositories.viewmodels.AboutViewModel

@Composable
fun AboutScreen(
    viewModel: AboutViewModel,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    AboutScreenMainComposable(
        viewModel.version,
        viewModel.dateOfBuild,
        viewModel.author,
        modifier,
        onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreenMainComposable(
    version: String,
    dateOfBuild: String,
    author: String,
    modifier: Modifier,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_about)) },
                modifier = modifier,
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
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.version),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = version,
                        modifier = Modifier.offset(x = 5.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.date_of_build),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = dateOfBuild,
                        modifier = Modifier.offset(x = 5.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.author),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = author,
                        modifier = Modifier.offset(x = 5.dp)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreenMainComposable(
        version = "1.0",
        dateOfBuild = "8. 3. 2023",
        author = "Iva Dibitanzlová",
        modifier = Modifier
    )
}