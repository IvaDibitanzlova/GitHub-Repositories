package cz.dibitanzlova.githubrepositories.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.dibitanzlova.githubrepositories.data.GitHubDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject internal constructor(
    private val savedStateHandle: SavedStateHandle,
    repository: GitHubDataRepository,
) : ViewModel() {

    // private val _status = MutableStateFlow(ResponseModel("", "", ""))
    //val status: StateFlow<ResponseModel> = _status.asStateFlow()

    init {
        viewModelScope.launch {

            val values = repository.getRepositories(userName = "IvaDibitanzlova")
            Log.d("test", "test - values " + values.size)
            for (i in 0 until values.size) {
                Log.d("test", "test - value " + values.get(i).name)
            }

            // _status.value = repository.getData()[0]
        }
    }
}