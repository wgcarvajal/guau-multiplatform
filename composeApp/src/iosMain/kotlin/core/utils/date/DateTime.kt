package core.utils.date


import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarOptions
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSGregorianCalendar
import platform.Foundation.NSTimeZone
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.timeZoneWithAbbreviation

actual object DateTime {
    actual fun getFormattedDate(date: Long, formatter: String): String {
        var dateNew = NSDate.dateWithTimeIntervalSince1970(date.toDouble() / 1000.0)
        val df = NSDateFormatter()
        df.timeZone = NSTimeZone.timeZoneWithAbbreviation("GMT")!!
        df.dateFormat = formatter
        return df.stringFromDate(dateNew)
    }

    actual fun getCurrentDate(): Long {
        val date = NSDate()
        return ((date.timeIntervalSince1970() * 1000.0)-(3600 * 5 * 1000)).toLong()
    }

    actual fun ageCalculateYears(date: Long): Int {
        val dateNew = NSDate.dateWithTimeIntervalSince1970(date.toDouble() / 1000.0)
        val currentDate = NSDate()
        val currentLong = ((currentDate.timeIntervalSince1970() * 1000.0)-(3600 * 5 * 1000)).toLong()
        val current = NSDate.dateWithTimeIntervalSince1970(currentLong.toDouble() / 1000.0)
        val calendar = NSCalendar(NSGregorianCalendar)
        val components = calendar.components(NSCalendarUnitYear, fromDate = dateNew, toDate = current, options = NSCalendarOptions.MIN_VALUE)
        return components.year.toInt()
    }

    actual fun ageCalculateMonths(date: Long): Int {
        val dateNew = NSDate.dateWithTimeIntervalSince1970(date.toDouble() / 1000.0)
        val currentDate = NSDate()
        val currentLong = ((currentDate.timeIntervalSince1970() * 1000.0)-(3600 * 5 * 1000)).toLong()
        val current = NSDate.dateWithTimeIntervalSince1970(currentLong.toDouble() / 1000.0)
        val calendar = NSCalendar(NSGregorianCalendar)
        val components = calendar.components(NSCalendarUnitMonth, fromDate = dateNew, toDate = current, options = NSCalendarOptions.MIN_VALUE)
        return components.month.toInt()
    }
}