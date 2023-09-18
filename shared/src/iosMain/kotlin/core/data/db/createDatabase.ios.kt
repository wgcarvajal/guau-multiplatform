package core.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.carpisoft.guau.Database

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "guau.db")
    }
}

fun createDatabase(): Database {
    return getDatabase(DriverFactory())
}