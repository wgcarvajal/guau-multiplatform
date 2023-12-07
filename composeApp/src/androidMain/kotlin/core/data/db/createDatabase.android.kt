package core.data.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.carpisoft.guau.Database

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "guau.db")
    }
}

fun createDatabase(context: Context):Database{
    return getDatabase(DriverFactory(context))
}