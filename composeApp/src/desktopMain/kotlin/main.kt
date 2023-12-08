import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.data.db.createDatabase
import core.data.preferences.createDataStore

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App(
            database = createDatabase(),
            datastore = createDataStore(),
            finishCallback = {},
            loginWithGoogle = {},
            signOutWithGoogle = {})
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App2()
}