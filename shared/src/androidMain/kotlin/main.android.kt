import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences

actual fun getPlatformName(): String = "Android"

@Composable
fun MainView(dataStore: DataStore<Preferences>) = App(datastore = dataStore)
