import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.AppNavigation
import navigation.AppNavigationRoute
import splash.domain.usecase.IsLoginTokenUseCase
import splash.ui.screens.SplashViewModel
import ui.theme.GuauTheme

@Composable
fun App() {
    GuauTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navigator = rememberNavigator()
            val splashViewModel = getViewModel(Unit,
                viewModelFactory {
                    SplashViewModel(
                        isLoginTokenUseCase = IsLoginTokenUseCase()
                    )
                })
            AppNavigation(
                navigator = navigator,
                splashViewModel= splashViewModel,
                launchLogin = {
                    navigator.navigate(route = AppNavigationRoute.LoginScreen.route)
                }
            )
        }
    }

}

expect fun getPlatformName(): String