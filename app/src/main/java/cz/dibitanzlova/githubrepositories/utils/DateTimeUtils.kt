package cz.dibitanzlova.githubrepositories.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

class DateTimeUtils {

    companion object {

        fun getFormattedTime(time: Long): String {
            return DateFormat.getDateInstance().format(time)
        }

        fun getParsedDateTime(time: String) : String? {
            val inputFormat = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            return try {
                DateFormat.getDateTimeInstance().format((inputFormat.parse(time) as Date))
            } catch (e: Exception) {
                null
            }
        }
    }
}