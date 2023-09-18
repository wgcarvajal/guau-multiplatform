import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import splash.ui.screens.SplashViewModel

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel,
    onShowTopBar: (Boolean) -> Unit,
    onShowBottomBar: (Boolean) -> Unit,
    launchLogin: () -> Unit,
    launchHome: () -> Unit,
    launchInitialSetup: () -> Unit
) {
    LaunchedEffect(key1 = 1) {
        onShowTopBar(false)
        onShowBottomBar(false)
    }
    ScreenPortrait()
    LaunchedEffect(key1 = 1) {
        delay(2000)
        splashViewModel.launchView()
    }
    val launchInitialSetup by splashViewModel.launchInitialSetup.collectAsState()
    if (launchInitialSetup) {
        splashViewModel.resetLaunchInitialSetup()
        launchInitialSetup()
    }
    val launchLogin by splashViewModel.launchLogin.collectAsState()
    if (launchLogin) {
        splashViewModel.resetLaunchLogin()
        launchLogin()
    }

    val launchHome by splashViewModel.launchHome.collectAsState()
    if (launchHome) {
        splashViewModel.resetLaunchHome()
        launchHome()
    }
}

@Composable
private fun ScreenPortrait() {

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center), text = "GUAU"
        )
    }
}