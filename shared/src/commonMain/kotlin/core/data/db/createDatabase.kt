package core.data.db

import app.cash.sqldelight.db.SqlDriver
import com.carpisoft.guau.Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun getDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(driver)
}