package com.carpisoft.guau.core.database.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(GuauDB.Schema, context, "guau.db")
    }
}

fun createDatabase(context: Context): GuauDB {
    return getDatabase(DriverFactory(context))
}