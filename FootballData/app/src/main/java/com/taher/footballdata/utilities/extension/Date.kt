package com.taher.footballdata.utilities.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*



@SuppressLint("SimpleDateFormat")
fun Date.toFormattedString(formatString: String, timeZone: TimeZone = TimeZone.getDefault()) : String {
    val format = SimpleDateFormat(formatString)
    format.timeZone = timeZone
    return format.format(this)
}

fun Date.getZeroTimeDate(): Date {
    val calendar = Calendar.getInstance()

    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    return calendar.time
}