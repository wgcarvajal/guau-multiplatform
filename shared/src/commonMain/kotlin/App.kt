import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.carpisoft.guau.Database
import com.carpisoft.guau.SharedRes
import core.data.preferences.PreferencesImpl
import core.domain.usecase.InitialsInCapitalLetterUseCase
import core.domain.usecase.IsMaxStringSizeUseCase
import core.domain.usecase.IsOnlyLettersUseCase
import core.domain.usecase.RemoveInitialWhiteSpaceUseCase
import core.navigation.AppNavigation
import core.navigation.AppNavigationRoute
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.foot.Foot
import core.ui.screens.header.HeadScaffold
import core.utils.constants.PlatformConstants
import core.utils.states.SignOutWithGoogleHandler
import core.utils.states.createStore
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.resources.compose.stringResource
import employee.data.repository.EmployeePreferencesRepository
import employee.domain.usecase.SaveCenterIdUseCase
import employee.domain.usecase.SaveEmployeeIdUseCase
import employee.domain.usecase.SaveRolUseCase
import initialsetup.data.network.repository.InitialSetupRepository
import initialsetup.domain.usecase.GetEmployeesUseCase
import initialsetup.domain.usecase.SaveCenterIdAndRolUseCase
import initialsetup.ui.screens.MyVetsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import login.data.repository.LoginAuthorizationRepository
import login.data.repository.LoginRepository
import login.data.repository.SignUpRepository
import login.data.repository.SocialLoginRepository
import login.domain.usecase.DoLoginUseCase
import login.domain.usecase.DoRegisterUseCase
import login.domain.usecase.DoSocialLoginUseCase
import login.domain.usecase.GetEmailUseCase
import login.domain.usecase.GetTokenUseCase
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import splash.domain.usecase.IsLoginTokenUseCase
import splash.domain.usecase.IsSelectedVetUseCase
import splash.ui.screens.SplashViewModel
import ui.AppViewModel
import ui.theme.GuauTheme

val store = CoroutineScope(SupervisorJob()).createStore()

@Composable
fun App(
    database: Database,
    datastore: DataStore<Preferences>,
    finishCallback: (() -> Unit)? = null,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit
) {
    GuauTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navigator = rememberNavigator()
            val httpClient = getHttpClient()
            val loginAuthorizationRepository =
                getLoginAuthorizationRepository(dataStore = datastore)
            val employeePreferencesRepository =
                getEmployeePreferencesRepository(dataStore = datastore)
            val splashViewModel =
                GetSplashViewModel(
                    loginAuthorizationRepository =
                    loginAuthorizationRepository,
                    employeePreferencesRepository =
                    employeePreferencesRepository
                )

            val loginViewModel = GetLoginViewModel(
                loginAuthorizationRepository = loginAuthorizationRepository,
                httpClient = httpClient
            )

            val signUpViewModel = GetSignUpViewModel(httpClient = httpClient)

            val appViewModel =
                GetAppViewModel(
                    loginAuthorizationRepository = loginAuthorizationRepository,
                    employeePreferencesRepository = employeePreferencesRepository
                )

            val myVetsViewModel = GetMyVetsViewModel(
                loginAuthorizationRepository = loginAuthorizationRepository,
                employeePreferencesRepository = employeePreferencesRepository,
                httpClient = httpClient
            )

            var showExitAlertDialog by rememberSaveable { mutableStateOf(false) }
            val title by appViewModel.title.collectAsState()
            var showNavigation by rememberSaveable { mutableStateOf(false) }
            var showExitCenter by rememberSaveable { mutableStateOf(false) }
            var showTopBar by rememberSaveable { mutableStateOf(false) }
            var showBottomBar by rememberSaveable { mutableStateOf(false) }
            var showAddActionButton by rememberSaveable { mutableStateOf(false) }
            var onClickAddActionButton: () -> Unit = {}
            val showActionNavigation: (Boolean) -> Unit = {
                showNavigation = it
            }
            val showActionFloatActionButton: (Boolean, () -> Unit) -> Unit = { show, action ->
                onClickAddActionButton = action
                showAddActionButton = show
            }

            val onBackOnClickConfirmation: () -> Unit = {
                navigator.popBackStack()
            }

            val onSetTitle: (String) -> Unit = {
                appViewModel.setTitle(it)
            }

            val onShowTopBar: (Boolean) -> Unit = {
                showTopBar = it
            }

            val onShowExitCenter: (Boolean) -> Unit = {
                showExitCenter = it
            }

            val onShowBottomBar: (Boolean) -> Unit = {
                showBottomBar = it
            }

            val signOffOnClick: () -> Unit = {
                appViewModel.showSignOffDialog(true)
            }
            val onExitVet: () -> Unit = {
                appViewModel.showExitCenterDialog(true)
            }

            val onBackShowDialog: () -> Unit = {
                showExitAlertDialog = true
            }

            val launchMyVets = {
                navigator.navigate(AppNavigationRoute.MyVetsScreen.route)
            }

            Scaffold(
                floatingActionButton = if (getPlatformName() == PlatformConstants.ANDROID && showAddActionButton) {
                    {
                        FloatingActionButton(
                            shape = RoundedCornerShape(50),
                            onClick = onClickAddActionButton
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "Add FAB",
                                tint = Color.White,
                            )
                        }
                    }
                } else {
                    {}
                },
                topBar = if (showTopBar) {
                    {
                        HeadScaffold(
                            title = title,
                            showNavigation = showNavigation,
                            showExitCenter = showExitCenter,
                            showButtonAddOnTopBar = showAddActionButton,
                            titleFontSize = 16.sp,
                            iconSize = 20.dp,
                            appBarHeight = 40.dp,
                            dropdownMenuWidth = 200.dp,
                            signOffOnClick = signOffOnClick,
                            onExitVet = onExitVet,
                            onBackOnClick = onBackOnClickConfirmation,
                            onAddOnClick = onClickAddActionButton
                        )
                    }
                } else {
                    {}
                },
                bottomBar = if (showBottomBar) {
                    {
                        Foot(
                            selectedIndex = 1
                        )
                    }
                } else {
                    {}
                }
            ) { contentPadding ->
                Box(modifier = Modifier.padding(contentPadding)) {
                    AppNavigation(
                        navigator = navigator,
                        appViewModel = appViewModel,
                        splashViewModel = splashViewModel,
                        loginViewModel = loginViewModel,
                        signUpViewModel = signUpViewModel,
                        myVetsViewModel = myVetsViewModel,
                        onShowTopBar = onShowTopBar,
                        onShowBottomBar = onShowBottomBar,
                        onShowExitCenter = onShowExitCenter,
                        showActionNavigation = showActionNavigation,
                        onSetTitle = onSetTitle,
                        showActionFloatActionButton = showActionFloatActionButton,
                        launchLogin = {
                            navigator.navigate(
                                route = AppNavigationRoute.LoginScreen.route, options = NavOptions(
                                    popUpTo = PopUpTo(
                                        route = appViewModel.currentScreenRoute,
                                        inclusive = true,
                                    ),
                                )
                            )
                        },
                        launchInitialSetup = {
                            navigator.navigate(
                                route = AppNavigationRoute.InitialScreen.route,
                                options = NavOptions(
                                    popUpTo = PopUpTo(
                                        route = appViewModel.currentScreenRoute,
                                        inclusive = true,
                                    ),
                                )
                            )
                        },
                        launchSignUp = {
                            navigator.navigate(
                                route = AppNavigationRoute.SignUpScreen.route
                            )
                        },
                        launchMyVets = launchMyVets,
                        onBack = { navigator.popBackStack() },
                        onBackShowDialog = onBackShowDialog,
                        launchHome = {
                            navigator.navigate(
                                route = AppNavigationRoute.HomeScreen.route,
                                options = NavOptions(
                                    popUpTo = PopUpTo(
                                        route = appViewModel.currentScreenRoute,
                                        inclusive = true,
                                    ),
                                )
                            )
                        },
                        loginWithGoogle = loginWithGoogle
                    )
                }
            }
            val showSignOffDialog by appViewModel.showSignOffDialog.collectAsState()
            TwoButtonDialog(
                show = showSignOffDialog,
                message = stringResource(SharedRes.strings.are_you_sure_you_want_to_log_out),
                confirmButtonText = stringResource(SharedRes.strings.ok),
                cancelButtonText = stringResource(SharedRes.strings.cancel),
                confirmButton = {
                    signOutWithGoogle()
                },
                onDismissRequest = {
                    appViewModel.showSignOffDialog(false)
                })

            val launchSplash: () -> Unit = {
                navigator.navigate(
                    route = AppNavigationRoute.SplashScreen.route,
                    options = NavOptions(
                        popUpTo = PopUpTo(
                            route = appViewModel.currentScreenRoute,
                            inclusive = true,
                        ),
                    )
                )
            }
            val successSignOff by appViewModel.successSignOff.collectAsState()
            if (successSignOff) {
                appViewModel.resetSuccessSignOff()
                launchSplash()
            }
            val successExitCenter by appViewModel.successExitCenter.collectAsState()
            if (successExitCenter) {
                appViewModel.resetSuccessExitCenter()
                launchSplash()
            }
            TwoButtonDialog(
                show = showExitAlertDialog,
                message = stringResource(SharedRes.strings.are_you_sure_you_want_to_exit_the_application),
                confirmButtonText = stringResource(SharedRes.strings.ok),
                cancelButtonText = stringResource(SharedRes.strings.cancel),
                confirmButton = {
                    showExitAlertDialog = false
                    if (finishCallback != null) {
                        finishCallback()
                    }
                },
                onDismissRequest = {
                    showExitAlertDialog = false
                })

            val showExitCenterDialog by appViewModel.showExitCenterDialog.collectAsState()
            TwoButtonDialog(
                show = showExitCenterDialog,
                message = stringResource(SharedRes.strings.are_you_sure_you_want_to_exit),
                confirmButtonText = stringResource(SharedRes.strings.ok),
                cancelButtonText = stringResource(SharedRes.strings.cancel),
                confirmButton = {
                    appViewModel.exitCenter()
                },
                onDismissRequest = {
                    appViewModel.showExitCenterDialog(false)
                })
            SignOutWithGoogleHandler {
                appViewModel.signOff()
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App2(){
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth().background(Color.Black), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Hello, ${getPlatformName()}"
                showImage = !showImage
            }) {
                Text(greetingText)
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    null
                )
            }
            Text(text = stringResource(SharedRes.strings.email), color = Color.White)
        }
    }
}


fun getHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
        }
    }
}

fun getLoginAuthorizationRepository(dataStore: DataStore<Preferences>): LoginAuthorizationRepository {
    return LoginAuthorizationRepository(
        preferences = PreferencesImpl(dataStore = dataStore)
    )
}

fun getEmployeePreferencesRepository(dataStore: DataStore<Preferences>): EmployeePreferencesRepository {
    return EmployeePreferencesRepository(
        preferences = PreferencesImpl(dataStore = dataStore)
    )
}

@Composable
fun GetSplashViewModel(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    employeePreferencesRepository: EmployeePreferencesRepository
): SplashViewModel {
    return getViewModel(SplashViewModel.KEY,
        viewModelFactory {
            SplashViewModel(
                isLoginTokenUseCase = IsLoginTokenUseCase(
                    loginAuthorizationPort = loginAuthorizationRepository
                ),
                isSelectedVetUseCase = IsSelectedVetUseCase(
                    employeePreferencesPort = employeePreferencesRepository
                )
            )
        })
}

@Composable
fun GetLoginViewModel(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    httpClient: HttpClient
): LoginViewModel {
    return getViewModel(
        LoginViewModel.KEY,
        viewModelFactory {
            LoginViewModel(
                validateEmailAndPasswordUseCase = ValidateEmailAndPasswordUseCase(),
                doLoginUseCase = DoLoginUseCase(
                    loginPort = LoginRepository(
                        httpClient = httpClient
                    )
                ),
                doSocialLoginUseCase = DoSocialLoginUseCase(
                    socialLoginPort = SocialLoginRepository(
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
}

@Composable
fun GetSignUpViewModel(httpClient: HttpClient): SignUpViewModel {
    return getViewModel(
        SignUpViewModel.KEY,
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
}

@Composable
fun GetAppViewModel(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    employeePreferencesRepository: EmployeePreferencesRepository
): AppViewModel {
    return getViewModel(
        AppViewModel.KEY,
        viewModelFactory {
            AppViewModel(
                saveEmailUseCase = SaveEmailUseCase(
                    loginAuthorizationPort =
                    loginAuthorizationRepository
                ),
                saveNameUseCase = SaveNameUseCase(
                    loginAuthorizationPort = loginAuthorizationRepository
                ),
                saveTokenUseCase = SaveTokenUseCase(
                    loginAuthorizationPort = loginAuthorizationRepository
                ),
                saveCenterIdUseCase = SaveCenterIdUseCase(
                    employeePreferencesPort = employeePreferencesRepository
                ),
                saveEmployeeIdUseCase = SaveEmployeeIdUseCase(
                    employeePreferencesPort = employeePreferencesRepository
                ),
                saveRolUseCase = SaveRolUseCase(
                    employeePreferencesPort = employeePreferencesRepository
                )
            )
        })
}

@Composable
fun GetMyVetsViewModel(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    employeePreferencesRepository: EmployeePreferencesRepository,
    httpClient: HttpClient
): MyVetsViewModel {
    return getViewModel(
        MyVetsViewModel.KEY,
        viewModelFactory {
            MyVetsViewModel(
                getTokenUseCase = GetTokenUseCase(loginAuthorizationPort = loginAuthorizationRepository),
                getEmailUseCase = GetEmailUseCase(loginAuthorizationPort = loginAuthorizationRepository),
                getEmployeesUseCase = GetEmployeesUseCase(
                    initialSetupPort = InitialSetupRepository(
                        httpClient = httpClient
                    )
                ),
                saveCenterIdAndRolUseCase = SaveCenterIdAndRolUseCase(employeePreferencesPort = employeePreferencesRepository)
            )
        })
}


expect fun getPlatformName(): String