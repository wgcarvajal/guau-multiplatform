package core.navigation

import SplashScreen
import androidx.compose.runtime.Composable
import login.ui.screens.LoginScreen
import login.ui.screens.LoginViewModel
import login.ui.screens.SignUpScreen
import login.ui.screens.SignUpViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import splash.ui.screens.SplashViewModel

@Composable
fun AppNavigation(
    navigator: Navigator,
    splashViewModel: SplashViewModel,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    launchLogin: () -> Unit,
    onClickSignUp: () -> Unit,
    onClickLink:()->Unit
) {
    NavHost(
        navigator = navigator,
        initialRoute = AppNavigationRoute.SplashScreen.route,
    ) {
        scene(route = AppNavigationRoute.SplashScreen.route) {
            SplashScreen(splashViewModel = splashViewModel, launchLogin = launchLogin)
        }

        scene(route = AppNavigationRoute.LoginScreen.route) {
            LoginScreen(loginViewModel = loginViewModel, onClickSignUp = onClickSignUp)
        }

        scene(route = AppNavigationRoute.SignUpScreen.route) {
            SignUpScreen(signUpViewModel = signUpViewModel, onClickLink = onClickLink)
        }
    }
}
