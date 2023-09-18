import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import com.carpisoft.guau.Database
import core.utils.states.Action

actual fun getPlatformName(): String = "Android"

@Composable
fun MainView(
    database: Database,
    dataStore: DataStore<Preferences>,
    finishCallback: () -> Unit,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit,
) =
    App(
        database = database,
        datastore = dataStore,
        finishCallback = finishCallback,
        loginWithGoogle = loginWithGoogle,
        signOutWithGoogle = signOutWithGoogle
    )

fun onLoginWithGoogle(token: String) {
    store.send(Action.OnLoginWithGoogle(param = token))
}

fun onSignOutWithGoogle() {
    store.send(Action.OnSignOutWithGoogle())
}
