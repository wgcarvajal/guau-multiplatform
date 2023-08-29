package navigation

import SplashScreen
import androidx.compose.runtime.Composable
import login.ui.screens.LoginScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import splash.ui.screens.SplashViewModel

@Composable
fun AppNavigation(
    navigator: Navigator,
    splashViewModel: SplashViewModel,
    launchLogin: () -> Unit
) {
    NavHost(
        navigator = navigator,
        initialRoute = AppNavigationRoute.SplashScreen.route,
    ) {
        scene(route = AppNavigationRoute.SplashScreen.route) {
            SplashScreen(splashViewModel = splashViewModel, launchLogin = launchLogin)
        }

        scene(route = AppNavigationRoute.LoginScreen.route) {
            LoginScreen()
        }
    }
}
