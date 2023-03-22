package cz.dibitanzlova.githubrepositories.network

import android.util.Log
import io.ktor.client.plugins.logging.*

class HttpLogger : Logger {
    override fun log(message: String) {
        if (message.length > 4000) {
            Log.d("Ktor", message.substring(0, 4000))
            log(message.substring(4000))
        } else {
            Log.d("Ktor", message)
        }
    }
}