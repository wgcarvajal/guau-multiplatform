import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import core.navigation.AppNavigation
import core.navigation.AppNavigationRoute
import core.ui.constants.ScreenEnum
import core.ui.model.UiStructureProperties
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.foot.Foot
import core.ui.screens.header.HeadScaffold
import core.ui.screens.viewmodels.GetAddCustomerViewModel
import core.ui.screens.viewmodels.GetAddPetViewModel
import core.ui.screens.viewmodels.GetAppViewModel
import core.ui.screens.viewmodels.GetLoginViewModel
import core.ui.screens.viewmodels.GetMyVetsViewModel
import core.ui.screens.viewmodels.GetSignUpViewModel
import core.ui.screens.viewmodels.GetSplashViewModel
import core.utils.constants.PlatformConstants
import core.utils.states.SignOutWithGoogleHandler
import core.utils.states.createStore
import dev.icerock.moko.resources.compose.stringResource
import employee.data.repository.EmployeePreferencesRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import login.data.repository.LoginAuthorizationRepository
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
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

            val addPetViewModel = GetAddPetViewModel(
                httpClient = httpClient,
                loginAuthorizationRepository = loginAuthorizationRepository
            )

            val addCustomerViewModel = GetAddCustomerViewModel(
                httpClient = httpClient,
                loginAuthorizationRepository = loginAuthorizationRepository
            )

            var showExitAlertDialog by rememberSaveable { mutableStateOf(false) }
            val title by appViewModel.title.collectAsState()
            var showNavigation by rememberSaveable { mutableStateOf(false) }
            var showAccountOptions by rememberSaveable { mutableStateOf(false) }
            var showNextAction by rememberSaveable { mutableStateOf(false) }
            var enableNextAction by rememberSaveable { mutableStateOf(false) }
            var showBottomAction by rememberSaveable { mutableStateOf(false) }
            var showSaveAction by rememberSaveable { mutableStateOf(false) }
            var enableSaveAction by rememberSaveable { mutableStateOf(false) }
            var showExitCenter by rememberSaveable { mutableStateOf(false) }
            var showTopBar by rememberSaveable { mutableStateOf(false) }
            var showBottomBar by rememberSaveable { mutableStateOf(false) }
            var showAddActionButton by rememberSaveable { mutableStateOf(false) }

            var onClickAddActionButton: () -> Unit = {
                when (title) {
                    ScreenEnum.Admissions -> {
                        navigator.navigate(
                            route = AppNavigationRoute.SelectPetScreen.route
                        )
                    }

                    ScreenEnum.Pets -> {
                        navigator.navigate(
                            route = AppNavigationRoute.SelectPetTypeScreen.route
                        )
                    }

                    ScreenEnum.Customers -> {
                        navigator.navigate(
                            route = AppNavigationRoute.AddCustomerScreen.route
                        )
                    }

                    else -> {

                    }
                }
            }
            val showActionNavigation: (Boolean) -> Unit = {
                showNavigation = it
            }

            val showActionAccountOptions: (Boolean) -> Unit = {
                showAccountOptions = it
            }

            val showActionNext: (Boolean) -> Unit = {
                showNextAction = it
            }
            val onEnabledNextAction: (Boolean) -> Unit = {
                enableNextAction = it
            }
            val onShowActionBottom: (Boolean) -> Unit = {
                showBottomAction = it
            }
            val onShowSaveAction: (Boolean) -> Unit = {
                showSaveAction = it
            }
            val onEnabledSaveAction: (Boolean) -> Unit = {
                enableSaveAction = it
            }
            val onShowAddActionButton: (Boolean) -> Unit = {
                showAddActionButton = it
            }

            val onBackOnClickConfirmation: () -> Unit = {
                navigator.popBackStack()
            }

            val onNextOnClick: () -> Unit = {
                when (title) {
                    ScreenEnum.SelectPetType -> {
                        navigator.navigate(AppNavigationRoute.SelectBreedScreen.route)
                    }

                    ScreenEnum.SelectBreed -> {
                        navigator.navigate(AppNavigationRoute.PetDataScreen.route)
                    }

                    else -> {

                    }
                }
            }

            val onSaveOnClick: () -> Unit = {
                when (title) {
                    else -> {

                    }
                }
            }

            val onSetTitle: (ScreenEnum) -> Unit = {
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

            val uiStructureProperties = UiStructureProperties(
                onShowTopBar = onShowTopBar,
                onShowExitCenter = onShowExitCenter,
                onShowBottomBar = onShowBottomBar,
                showActionNavigation = showActionNavigation,
                showActionAccountOptions = showActionAccountOptions,
                showAddActionButton = onShowAddActionButton,
                onShowActionBottom = onShowActionBottom,
                showActionNext = showActionNext,
                onEnabledNextAction = onEnabledNextAction,
                onShowSaveAction = onShowSaveAction,
                onEnabledSaveAction = onEnabledSaveAction,
                onSetTitle = onSetTitle
            )

            Scaffold(
                snackbarHost = {

                },
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
                            title = getTitle(title),
                            showAccountOptions = showAccountOptions,
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
                Box(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(
                            bottom = if (showBottomAction) {
                                80.dp
                            } else {
                                0.dp
                            }
                        )
                    ) {
                        AppNavigation(
                            navigator = navigator,
                            uiStructureProperties = uiStructureProperties,
                            appViewModel = appViewModel,
                            splashViewModel = splashViewModel,
                            loginViewModel = loginViewModel,
                            signUpViewModel = signUpViewModel,
                            myVetsViewModel = myVetsViewModel,
                            addPetViewModel = addPetViewModel,
                            addCustomerViewModel = addCustomerViewModel,
                            launchLogin = {
                                navigator.navigate(
                                    route = AppNavigationRoute.LoginScreen.route,
                                    options = NavOptions(
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
                            loginWithGoogle = loginWithGoogle,
                            onAdmission = {
                                navigator.navigate(
                                    route = AppNavigationRoute.AdmissionScreen.route
                                )
                            },
                            onSelectAction = {
                                when (title) {
                                    ScreenEnum.SelectPet -> {
                                        navigator.navigate(route = AppNavigationRoute.PetsScreen.route)
                                    }

                                    ScreenEnum.SelectPetType -> {
                                        navigator.navigate(route = AppNavigationRoute.SpeciesScreen.route)
                                    }

                                    ScreenEnum.SelectBreed -> {
                                        navigator.navigate(route = AppNavigationRoute.BreedsScreen.route)
                                    }

                                    ScreenEnum.PetData -> {
                                        navigator.navigate(route = AppNavigationRoute.CustomersScreen.route)
                                    }

                                    else -> {

                                    }
                                }

                            }
                        )
                    }
                    if (showBottomAction) {
                        Box(Modifier.fillMaxWidth().height(80.dp).align(Alignment.BottomCenter)) {
                            if (showNextAction) {
                                Button(
                                    onClick = onNextOnClick,
                                    modifier = Modifier.align(Alignment.TopEnd)
                                        .padding(end = 20.dp),
                                    enabled = enableNextAction
                                ) {
                                    Text(text = stringResource(SharedRes.strings.next))
                                }
                            }
                            if (showSaveAction) {
                                Button(
                                    onClick = onSaveOnClick,
                                    modifier = Modifier.align(Alignment.TopEnd)
                                        .padding(end = 20.dp),
                                    enabled = enableSaveAction
                                ) {
                                    Text(text = stringResource(SharedRes.strings.save))
                                }
                            }
                        }
                    }

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
fun getTitle(screenEnum: ScreenEnum): String {
    return when (screenEnum) {
        ScreenEnum.MyVets -> {
            stringResource(SharedRes.strings.my_vets)
        }

        ScreenEnum.Home -> {
            stringResource(SharedRes.strings.home)
        }

        ScreenEnum.Admissions -> {
            stringResource(SharedRes.strings.admissions)
        }

        ScreenEnum.SelectPet -> {
            stringResource(SharedRes.strings.select_pet)
        }

        ScreenEnum.Pets -> {
            stringResource(SharedRes.strings.pets)
        }

        ScreenEnum.SelectPetType, ScreenEnum.SelectBreed, ScreenEnum.PetData -> {
            stringResource(SharedRes.strings.add_pet)
        }

        ScreenEnum.PetTypes -> {
            stringResource(SharedRes.strings.pet_kind)
        }

        ScreenEnum.Breeds -> {
            stringResource(SharedRes.strings.breeds)
        }

        ScreenEnum.Customers -> {
            stringResource(SharedRes.strings.customers)
        }

        ScreenEnum.AddCustomer -> {
            stringResource(SharedRes.strings.customer_registry)
        }

        else -> {
            ""
        }
    }
}

expect fun getPlatformName(): String