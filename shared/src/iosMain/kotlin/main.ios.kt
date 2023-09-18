import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.carpisoft.guau.Database
import moe.tlaster.precompose.PreComposeApplication

actual fun getPlatformName(): String = "iOS"

fun MainViewController(
    database: Database,
    datastore: DataStore<Preferences>,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit
) =
    PreComposeApplication() {
        App(
            database = database,
            datastore = datastore,
            loginWithGoogle = loginWithGoogle,
            signOutWithGoogle = signOutWithGoogle
        )
    }