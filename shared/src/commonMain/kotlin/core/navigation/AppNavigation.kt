package core.navigation

import SplashScreen
import androidx.compose.runtime.Composable
import initialsetup.ui.screens.InitialScreen
import initialsetup.ui.screens.MyVetsScreen
import initialsetup.ui.screens.MyVetsViewModel
import login.ui.screens.LoginScreen
import login.ui.screens.LoginViewModel
import login.ui.screens.SignUpScreen
import login.ui.screens.SignUpViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import splash.ui.screens.SplashViewModel
import ui.AppViewModel

@Composable
fun AppNavigation(
    navigator: Navigator,
    appViewModel: AppViewModel,
    splashViewModel: SplashViewModel,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    myVetsViewModel: MyVetsViewModel,
    onShowTopBar: (Boolean) -> Unit,
    showActionNavigation: (Boolean) -> Unit,
    onSetTitle: (String) -> Unit,
    showActionFloatActionButton: (Boolean, () -> Unit) -> Unit,
    launchLogin: () -> Unit,
    launchInitialSetup: () -> Unit,
    launchSignUp: () -> Unit,
    launchMyVets: () -> Unit,
    onBack: () -> Unit,
    onBackShowDialog: () -> Unit
) {
    NavHost(
        navigator = navigator,
        initialRoute = AppNavigationRoute.SplashScreen.route,
    ) {
        scene(route = AppNavigationRoute.SplashScreen.route) {
            appViewModel.currentScreenRoute = AppNavigationRoute.SplashScreen.route
            SplashScreen(
                splashViewModel = splashViewModel,
                onShowTopBar = onShowTopBar,
                launchLogin = launchLogin,
                launchInitialSetup = launchInitialSetup
            )
        }

        scene(route = AppNavigationRoute.LoginScreen.route) {
            appViewModel.currentScreenRoute = AppNavigationRoute.LoginScreen.route
            LoginScreen(
                loginViewModel = loginViewModel,
                onShowTopBar = onShowTopBar,
                loginSuccess = launchInitialSetup,
                onClickSignUp = launchSignUp
            )
        }

        scene(route = AppNavigationRoute.SignUpScreen.route) {
            appViewModel.currentScreenRoute = AppNavigationRoute.SignUpScreen.route
            SignUpScreen(
                signUpViewModel = signUpViewModel,
                onShowTopBar = onShowTopBar,
                onClickLink = onBack
            )
        }

        scene(route = AppNavigationRoute.InitialScreen.route)
        {
            appViewModel.currentScreenRoute = AppNavigationRoute.InitialScreen.route
            InitialScreen(
                onShowTopBar = onShowTopBar,
                showNavigation = showActionNavigation,
                myVetsOnClick = launchMyVets,
                showFloatActionButton = showActionFloatActionButton,
                onSetTitle = onSetTitle,
                onBack = onBackShowDialog
            )
        }

        scene(route = AppNavigationRoute.MyVetsScreen.route)
        {
            appViewModel.currentScreenRoute = AppNavigationRoute.MyVetsScreen.route
            MyVetsScreen(
                myVetsViewModel = myVetsViewModel,
                onShowTopBar = onShowTopBar,
                showNavigation = showActionNavigation,
                showFloatActionButton = showActionFloatActionButton,
                onSetTitle = onSetTitle,
                onBackOnClick = onBack
            )
        }
    }
}
