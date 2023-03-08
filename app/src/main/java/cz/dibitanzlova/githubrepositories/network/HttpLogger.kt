package cz.dibitanzlova.githubrepositories.network

import android.util.Log
import io.ktor.client.plugins.logging.*

class HttpLogger: Logger {
    override fun log(message: String) {
        Log.d("Ktor", message)
    }
}