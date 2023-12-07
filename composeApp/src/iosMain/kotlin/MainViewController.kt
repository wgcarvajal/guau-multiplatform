import androidx.compose.ui.window.ComposeUIViewController
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.carpisoft.guau.Database
import core.utils.states.Action

fun MainViewController(
    database: Database,
    datastore: DataStore<Preferences>,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit
) = ComposeUIViewController {
    App(
        database = database,
        datastore = datastore,
        loginWithGoogle = loginWithGoogle,
        signOutWithGoogle = signOutWithGoogle
    )
}

fun onLoginWithGoogle(token: String) {
    store.send(Action.OnLoginWithGoogle(param = token))
}

fun onSignOutWithGoogle() {
    store.send(Action.OnSignOutWithGoogle())
}
