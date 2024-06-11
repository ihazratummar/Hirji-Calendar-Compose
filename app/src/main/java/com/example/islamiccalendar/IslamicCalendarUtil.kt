package com.example.islamiccalendar

import android.icu.util.Calendar
import android.icu.util.IslamicCalendar
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
fun getIslamicDate(): String {
    val islamicCalendar = IslamicCalendar()
    val day = islamicCalendar.get(Calendar.DAY_OF_MONTH)
    val month = islamicCalendar.get(Calendar.MONTH)
    val year = islamicCalendar.get(Calendar.YEAR)

    return "$day ${getIslamicMonthName(month)} $year"
}

@RequiresApi(Build.VERSION_CODES.N)
fun getIslamicMonthName(month: Int): String {
    val monthNames = listOf(
        "Muharram", "Safar", "Rabi' al-awwal", "Rabi' al-thani",
        "Jumada al-awwal", "Jumada al-thani", "Rajab", "Sha'ban",
        "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
    )
    // Adjusting the month index to match the IslamicCalendar class
    val adjustedMonth = if (month == IslamicCalendar.MUHARRAM) 0 else month
    return monthNames.getOrElse(adjustedMonth) { "" } // Safe access to avoid ArrayIndexOutOfBoundsException
}

@RequiresApi(Build.VERSION_CODES.N)
fun getIslamicCalendarForMonths(): List<Pair<String, List<String>>> {
    val islamicCalendarData = mutableListOf<Pair<String, List<String>>>()

    val currentIslamicCalendar = IslamicCalendar()
    var currentYear = currentIslamicCalendar.get(IslamicCalendar.YEAR)
    var currentMonth = IslamicCalendar.MUHARRAM // Start from Muharram

    repeat(12) { // Iterate through all 12 months
        val daysList = mutableListOf<String>()

        val calendar = IslamicCalendar()
        calendar.set(IslamicCalendar.YEAR, currentYear)
        calendar.set(IslamicCalendar.MONTH, currentMonth)

        val monthName = getIslamicMonthName(currentMonth)
        val daysInMonth = calendar.getActualMaximum(IslamicCalendar.DAY_OF_MONTH)

        // Set the calendar to the first day of the month
        calendar.set(IslamicCalendar.DAY_OF_MONTH, 1)

        // Determine the starting day of the week for the current month
        val startingDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Determine the correct starting position in daysList based on the starting day of the week
        val daysToAdd = if (startingDayOfWeek == Calendar.SUNDAY) {
            0
        } else {
            startingDayOfWeek - Calendar.SUNDAY
        }

        // Add empty cells for the start of the month to align the days with the correct weekday
        repeat(daysToAdd) {
            daysList.add("")
        }

        // Populate days of the month
        repeat(daysInMonth) { day ->
            calendar.set(IslamicCalendar.DAY_OF_MONTH, day + 1)
            daysList.add((day + 1).toString())
        }

        islamicCalendarData.add(monthName to daysList)

        // Move to the next month
        currentMonth++
        if (currentMonth > IslamicCalendar.DHU_AL_HIJJAH) {
            currentMonth = IslamicCalendar.MUHARRAM
            currentYear++
        }
    }

    return islamicCalendarData
}
