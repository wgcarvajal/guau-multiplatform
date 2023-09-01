import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import moe.tlaster.precompose.PreComposeApplication

actual fun getPlatformName(): String = "iOS"

fun MainViewController(datastore:DataStore<Preferences>) = PreComposeApplication() { App(datastore = datastore) }