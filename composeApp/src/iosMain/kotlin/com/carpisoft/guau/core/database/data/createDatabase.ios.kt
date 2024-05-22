package com.carpisoft.guau.core.database.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(GuauDB.Schema, "guau.db")
    }
}

fun createDatabase(): GuauDB {
    return getDatabase(DriverFactory())
}