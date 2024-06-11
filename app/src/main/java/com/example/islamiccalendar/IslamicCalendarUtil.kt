package com.example.islamiccalendar

import android.icu.util.IslamicCalendar
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
fun getIslamicDate(): String {
    val islamicCalendar = IslamicCalendar()
    val day = islamicCalendar.get(Calendar.DAY_OF_MONTH)
    val month = islamicCalendar.get(Calendar.MONTH)
    val year = islamicCalendar.get(Calendar.YEAR)

    val monthNames = listOf(
        "Muharram", "Safar", "Rabi' al-awwal", "Rabi' al-thani",
        "Jumada al-awwal", "Jumada al-thani", "Rajab", "Sha'ban",
        "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
    )

    return "$day ${monthNames[month]} $year AH"
}

@RequiresApi(Build.VERSION_CODES.N)
fun getIslamicCalendarForMonth(): List<String> {
    val islamicCalendar = IslamicCalendar()
    islamicCalendar.set(Calendar.DAY_OF_MONTH, 1)
    val firstDayOfWeek = islamicCalendar.get(Calendar.DAY_OF_WEEK)
    val daysInMonth = islamicCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val month = islamicCalendar.get(Calendar.MONTH)
    val year = islamicCalendar.get(Calendar.YEAR)

    val monthNames = listOf(
        "Muharram", "Safar", "Rabi' al-awwal", "Rabi' al-thani",
        "Jumada al-awwal", "Jumada al-thani", "Rajab", "Sha'ban",
        "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
    )

    val calendarList = mutableListOf<String>()

    // Add empty strings for days before the first day of the current month
    for (i in 1 until firstDayOfWeek) {
        calendarList.add("")
    }

    // Add the actual days of the month
    for (day in 1..daysInMonth) {
        calendarList.add("$day")
    }

    return calendarList
}
