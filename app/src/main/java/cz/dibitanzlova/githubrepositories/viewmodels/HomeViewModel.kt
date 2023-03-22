package cz.dibitanzlova.githubrepositories.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.dibitanzlova.githubrepositories.data.GitHubDataRepository
import cz.dibitanzlova.githubrepositories.model.HomeState
import cz.dibitanzlova.githubrepositories.utils.TextValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject internal constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: GitHubDataRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState("", emptyList()))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    // update the query so user can see changes on screen
    fun onQueryChange(query: String) {
        _state.update { currentState ->
            currentState.copy(query = query)
        }
    }

    // searching for repositories for a given userName
    fun onSearch(query: String) {
        viewModelScope.launch {

            // validation - if it's not correct username on GitHub, return
            if (!TextValidator.isCorrectGitUsername(query)) {
                showError(Error.SHOW_NO_USER_FOUND)
                return@launch
            }

            // show loading indicator
            showProgress()

            val repositoryResponse = repository.getRepositories(query)
            if (repositoryResponse.listRepositories.isEmpty()) {
                when (repositoryResponse.httpStatusCode.value) {
                    404 -> {
                        // no user with a given userName was found
                        showError(Error.SHOW_NO_USER_FOUND)
                    }
                    0 -> {
                        // no internet connection
                        showError(Error.SHOW_NO_CONNECTION)
                    }
                    else -> {
                        // no public repositories for a given userName were found
                        showError(Error.SHOW_NO_REPOSITORIES_FOUND)
                    }
                }
            } else {
                // public repositories were found
                _state.update { currentState ->
                    currentState.copy(
                        repositories = repositoryResponse.listRepositories,
                        showNoRepositoriesFound = false,
                        showNoConnection = false,
                        showNoUserFound = false,
                        isProgressShown = false
                    )
                }
            }
        }
    }

    private fun showError(error: Error) {
        _state.update { currentState ->
            currentState.copy(
                repositories = emptyList(),
                showNoRepositoriesFound = error == Error.SHOW_NO_REPOSITORIES_FOUND,
                showNoConnection = error == Error.SHOW_NO_CONNECTION,
                showNoUserFound = error == Error.SHOW_NO_USER_FOUND,
                isProgressShown = false
            )
        }
    }

    private fun showProgress() {
        _state.update { currentState ->
            currentState.copy(
                repositories = emptyList(),
                showNoConnection = false,
                showNoUserFound = false,
                showNoRepositoriesFound = false,
                isProgressShown = true
            )
        }
    }

    private enum class Error {
        SHOW_NO_REPOSITORIES_FOUND, SHOW_NO_CONNECTION, SHOW_NO_USER_FOUND
    }
}