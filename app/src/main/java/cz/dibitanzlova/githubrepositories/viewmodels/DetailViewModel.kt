package cz.dibitanzlova.githubrepositories.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.dibitanzlova.githubrepositories.data.GitHubDataRepository
import cz.dibitanzlova.githubrepositories.model.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: GitHubDataRepository,
) : ViewModel() {

    private val userName: String = savedStateHandle.get<String>(USERNAME_SAVED_STATE_KEY)!!
    private val repositoryName: String = savedStateHandle.get<String>(REPOSITORY_NAME_SAVED_STATE_KEY)!!

    private val _state = MutableStateFlow(DetailState(repositoryName, emptyList(), emptyList()))
    val state: StateFlow<DetailState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            // show progress indicators
            showProgress()

            // load data and dismiss progress indicators
            _state.update { currentState ->
                currentState.copy(
                    branches = repository.getBranches(userName, repositoryName),
                    isBranchesProgressShown = false
                )
            }
            val commitResponse = repository.getCommits(userName, repositoryName)
            if (commitResponse.httpStatusCode.value == 0) {
                // no internet connection
                showConnectionError()
            } else {
                _state.update { currentState ->
                    currentState.copy(
                        commits = commitResponse.listCommits,
                        isCommitsProgressShown = false,
                        showNoConnection = false
                    )
                }
            }
        }
    }

    private fun showProgress() {
        _state.update { currentState ->
            currentState.copy(
                isBranchesProgressShown = true,
                isCommitsProgressShown = true,
                showNoConnection = false
            )
        }
    }

    private fun showConnectionError() {
        _state.update { currentState ->
            currentState.copy(
                branches = emptyList(),
                commits = emptyList(),
                isBranchesProgressShown = false,
                isCommitsProgressShown = false,
                showNoConnection = true
            )
        }
    }

    companion object {
        private const val USERNAME_SAVED_STATE_KEY = "userName"
        private const val REPOSITORY_NAME_SAVED_STATE_KEY = "repositoryName"
    }
}