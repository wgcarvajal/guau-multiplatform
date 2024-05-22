package com.carpisoft.guau.core.database.data

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun getDatabase(driverFactory: DriverFactory): GuauDB {
    val driver = driverFactory.createDriver()
    return GuauDB(driver)
}