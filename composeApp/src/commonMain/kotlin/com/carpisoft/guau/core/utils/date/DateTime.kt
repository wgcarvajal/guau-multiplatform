package com.carpisoft.guau.core.utils.date
expect object DateTime {
    fun getFormattedDate(
        date: Long,
        formatter:String
    ): String

    fun getCurrentDate(
    ): Long

    fun ageCalculateYears(date:Long):Int

    fun ageCalculateMonths(date:Long):Int
}