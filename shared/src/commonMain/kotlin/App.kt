import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import core.data.preferences.PreferencesImpl
import core.domain.usecase.InitialsInCapitalLetterUseCase
import core.domain.usecase.IsMaxStringSizeUseCase
import core.domain.usecase.IsOnlyLettersUseCase
import core.domain.usecase.RemoveInitialWhiteSpaceUseCase
import core.navigation.AppNavigation
import core.navigation.AppNavigationRoute
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import login.data.repository.LoginAuthorizationRepository
import login.data.repository.LoginRepository
import login.data.repository.SignUpRepository
import login.domain.usecase.DoLoginUseCase
import login.domain.usecase.DoRegisterUseCase
import login.domain.usecase.SaveEmailUseCase
import login.domain.usecase.SaveNameUseCase
import login.domain.usecase.SaveTokenUseCase
import login.domain.usecase.ValidateEmailAndPasswordUseCase
import login.domain.usecase.ValidateNameUseCase
import login.ui.screens.LoginViewModel
import login.ui.screens.SignUpViewModel
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import splash.domain.usecase.IsLoginTokenUseCase
import splash.ui.screens.SplashViewModel
import ui.theme.GuauTheme

@Composable
fun App(datastore: DataStore<Preferences>) {
    GuauTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navigator = rememberNavigator()
            val loginAuthorizationRepository = LoginAuthorizationRepository(
                preferences = PreferencesImpl(dataStore = datastore)
            )
            val splashViewModel = getViewModel(Unit,
                viewModelFactory {
                    SplashViewModel(
                        isLoginTokenUseCase = IsLoginTokenUseCase(
                            loginAuthorizationPort = loginAuthorizationRepository
                        )
                    )
                })
            val httpClient = HttpClient {
                install(ContentNegotiation) {
                    json()
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 30000
                }
            }
            val loginViewModel = getViewModel(Unit,
                viewModelFactory {
                    LoginViewModel(
                        validateEmailAndPasswordUseCase = ValidateEmailAndPasswordUseCase(),
                        doLoginUseCase = DoLoginUseCase(
                            loginPort = LoginRepository(
                                httpClient = httpClient
                            )
                        ),
                        saveEmailUseCase = SaveEmailUseCase(
                            loginAuthorizationPort = loginAuthorizationRepository
                        ),
                        saveNameUseCase = SaveNameUseCase(
                            loginAuthorizationPort = loginAuthorizationRepository
                        ),
                        saveTokenUseCase = SaveTokenUseCase(loginAuthorizationPort = loginAuthorizationRepository)
                    )
                })
            val signUpViewModel = getViewModel(Unit,
                viewModelFactory {
                    SignUpViewModel(
                        doRegisterUseCase = DoRegisterUseCase(
                            signUpPort = SignUpRepository(
                                httpClient = httpClient
                            )
                        ),
                        initialsInCapitalLetterUseCase = InitialsInCapitalLetterUseCase(),
                        removeInitialWhiteSpaceUseCase = RemoveInitialWhiteSpaceUseCase(),
                        isOnlyLettersUseCase = IsOnlyLettersUseCase(),
                        isMaxStringSizeUseCase = IsMaxStringSizeUseCase(),
                        validateEmailAndPasswordUseCase = ValidateEmailAndPasswordUseCase(),
                        validateNameUseCase = ValidateNameUseCase()
                    )
                })
            AppNavigation(
                navigator = navigator,
                splashViewModel = splashViewModel,
                loginViewModel = loginViewModel,
                signUpViewModel = signUpViewModel,
                launchLogin = {
                    navigator.navigate(
                        route = AppNavigationRoute.LoginScreen.route, options = NavOptions(
                            popUpTo = PopUpTo(
                                route = AppNavigationRoute.SplashScreen.route,
                                inclusive = true,
                            ),
                        )
                    )
                },
                onClickSignUp = {
                    navigator.navigate(route = AppNavigationRoute.SignUpScreen.route)
                },
                onClickLink = {
                    navigator.popBackStack()
                }
            )
        }
    }

}

expect fun getPlatformName(): String