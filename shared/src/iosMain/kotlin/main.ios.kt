import androidx.compose.ui.window.ComposeUIViewController
import core.utils.constants.PlatformConstants
import core.utils.states.Action

actual fun getPlatformName(): String = PlatformConstants.IOS

fun MainViewController(
    /*database: Database,
    datastore: DataStore<Preferences>,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit*/
) =
    ComposeUIViewController {
        App2(
            /*database = database,
            datastore = datastore,
            loginWithGoogle = loginWithGoogle,
            signOutWithGoogle = signOutWithGoogle*/
        )
    }

fun onLoginWithGoogle(token: String) {
    store.send(Action.OnLoginWithGoogle(param = token))
}

fun onSignOutWithGoogle() {
    store.send(Action.OnSignOutWithGoogle())
}