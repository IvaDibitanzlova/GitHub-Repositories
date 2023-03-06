package cz.dibitanzlova.githubrepositories.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import cz.dibitanzlova.githubrepositories.data.GitHubDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject internal constructor(
    private val savedStateHandle: SavedStateHandle,
    repository: GitHubDataRepository,
) : ViewModel() {
}