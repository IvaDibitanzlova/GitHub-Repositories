package cz.dibitanzlova.githubrepositories.compose.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cz.dibitanzlova.githubrepositories.R
import cz.dibitanzlova.githubrepositories.viewmodels.AboutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    viewModel: AboutViewModel,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val version = viewModel.version
    val dateOfBuild = viewModel.dateOfBuild
    val author = viewModel.author

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
                modifier = Modifier.padding(innerPadding).fillMaxWidth().fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = stringResource(R.string.version),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(text = version,
                        modifier = Modifier.offset(x = 5.dp))
                }
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(4.dp)) {
                    Text(text = stringResource(R.string.date_of_build),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(text = dateOfBuild,
                        modifier = Modifier.offset(x = 5.dp))
                }
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(4.dp)) {
                    Text(text = stringResource(R.string.author),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(text = author,
                        modifier = Modifier.offset(x = 5.dp))
                }
            }
        }
    )

}