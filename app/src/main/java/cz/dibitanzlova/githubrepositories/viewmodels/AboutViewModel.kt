package cz.dibitanzlova.githubrepositories.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import cz.dibitanzlova.githubrepositories.BuildConfig
import cz.dibitanzlova.githubrepositories.utils.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject internal constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val author = "Iva Dibitanzlová"
    val version = BuildConfig.VERSION_NAME
    val dateOfBuild: String = DateTimeUtils.getFormattedTime(BuildConfig.BUILD_TIME.toLong())

}