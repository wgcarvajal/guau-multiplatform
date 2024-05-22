package com.carpisoft.guau.core.utils.date

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

actual object DateTime {
    @JvmStatic
    actual fun getFormattedDate(
        date: Long,
        formatter: String
    ): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        val dateFormatter = SimpleDateFormat(formatter)
        dateFormatter.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormatter.format(calendar.time)
    }

    @JvmStatic
    actual fun getCurrentDate(): Long {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        return calendar.timeInMillis - (3500 * 5 * 1000)
    }

    @JvmStatic
    actual fun ageCalculateYears(date: Long): Int {
        val current = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        var age = current.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
        if (age > 0 && (calendar.get(Calendar.MONTH) > current.get(Calendar.MONTH)) || (calendar.get(
                Calendar.MONTH
            ) == current.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH) > current.get(
                Calendar.DAY_OF_MONTH
            ))
        ) {
            age -= 1
        }
        return age
    }

    actual fun ageCalculateMonths(date: Long): Int {
        val current = Calendar.getInstance()
        current.time = Date()
        current.timeInMillis -= (3500 * 5 * 1000)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        calendar.timeZone = TimeZone.getTimeZone("GMT")
        var years = current.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
        if (years > 0 && (calendar.get(Calendar.MONTH) > current.get(Calendar.MONTH)) || (calendar.get(
                Calendar.MONTH
            ) == current.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH) > current.get(
                Calendar.DAY_OF_MONTH
            ))
        ) {
            years -= 1
        }
        var month = (years * 12) + (current.get(Calendar.MONTH) - calendar.get(Calendar.MONTH))
        if (month > 0 && calendar.get(Calendar.DAY_OF_MONTH) > current.get(Calendar.DAY_OF_MONTH)) {
            month -= 1
        }
        return month
    }
}