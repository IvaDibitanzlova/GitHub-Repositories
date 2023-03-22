package cz.dibitanzlova.githubrepositories.utils

private const val GIT_USERNAME_REGEX = "^[A-Za-z][A-Za-z0-9_-]{1,39}\$"

class TextValidator {

    companion object {

        fun isCorrectGitUsername(username: String) : Boolean {
            return username.matches(Regex(GIT_USERNAME_REGEX))
        }
    }
}