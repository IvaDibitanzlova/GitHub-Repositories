package cz.dibitanzlova.githubrepositories.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.dibitanzlova.githubrepositories.data.GitHubDataRepository
import cz.dibitanzlova.githubrepositories.model.HomeState
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

    private val userNameRegex = "^[A-Za-z][A-Za-z0-9_-]{1,39}\$"

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
            if (!query.matches(Regex(userNameRegex))) {
                _state.update { currentState ->
                    currentState.copy(
                        repositories = emptyList(),
                        showNoUserFound = true,
                        showNoRepositoriesFound = false,
                        isProgressShown = false
                    )
                }
                return@launch
            }

            // show loading indicator
            _state.update { currentState ->
                currentState.copy(
                    repositories = emptyList(),
                    showNoUserFound = false,
                    showNoRepositoriesFound = false,
                    isProgressShown = true
                )
            }

            val repositoryResponse = repository.getRepositories(query)
            if (repositoryResponse.listRepositories.isEmpty()) {
                if (repositoryResponse.httpStatusCode.value == 404) {
                    // no user with a given userName was found
                    _state.update { currentState ->
                        currentState.copy(
                            repositories = emptyList(),
                            showNoUserFound = true,
                            showNoRepositoriesFound = false,
                            showNoConnection = false,
                            isProgressShown = false
                        )
                    }
                } else if (repositoryResponse.httpStatusCode.value == 0) {
                    // no internet connection
                    _state.update { currentState ->
                        currentState.copy(
                            repositories = emptyList(),
                            showNoUserFound = false,
                            showNoRepositoriesFound = false,
                            showNoConnection = true,
                            isProgressShown = false
                        )
                    }
                } else {
                    // no public repositories for a given userName were found
                    _state.update { currentState ->
                        currentState.copy(
                            repositories = emptyList(),
                            showNoRepositoriesFound = true,
                            showNoConnection = false,
                            showNoUserFound = false,
                            isProgressShown = false
                        )
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
}