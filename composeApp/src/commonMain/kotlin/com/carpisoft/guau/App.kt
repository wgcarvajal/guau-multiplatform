package com.carpisoft.guau

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.carpisoft.guau.core.navigation.AppNavigation
import com.carpisoft.guau.core.navigation.AppNavigationRoute
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.foot.Foot
import com.carpisoft.guau.core.ui.screens.header.HeadScaffold
import com.carpisoft.guau.core.utils.constants.PlatformConstants
import com.carpisoft.guau.core.utils.states.SignOutWithGoogleHandler
import com.carpisoft.guau.core.utils.states.createStore
import com.carpisoft.guau.customer.ui.GetAddCustomerViewModel
import com.carpisoft.guau.customer.ui.GetCustomerViewModel
import com.carpisoft.guau.initialsetup.ui.screens.GetMyVetsViewModel
import com.carpisoft.guau.login.ui.screens.GetLoginViewModel
import com.carpisoft.guau.login.ui.screens.GetSignUpViewModel
import com.carpisoft.guau.pet.ui.screens.GetAddPetViewModel
import com.carpisoft.guau.pet.ui.screens.GetPetsViewModel
import com.carpisoft.guau.splash.ui.screens.GetSplashViewModel
import com.carpisoft.guau.splash.ui.screens.SplashScreen
import com.carpisoft.guau.ui.GetAppViewModel
import com.carpisoft.guau.ui.theme.GuauTheme
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.add_pet
import guau.composeapp.generated.resources.admissions
import guau.composeapp.generated.resources.age
import guau.composeapp.generated.resources.are_you_sure_you_want_to_cancel_the_registration_process
import guau.composeapp.generated.resources.are_you_sure_you_want_to_exit
import guau.composeapp.generated.resources.are_you_sure_you_want_to_exit_the_application
import guau.composeapp.generated.resources.are_you_sure_you_want_to_log_out
import guau.composeapp.generated.resources.breeds
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.compose_multiplatform
import guau.composeapp.generated.resources.customer_registry
import guau.composeapp.generated.resources.customers
import guau.composeapp.generated.resources.home
import guau.composeapp.generated.resources.my_vets
import guau.composeapp.generated.resources.next
import guau.composeapp.generated.resources.ok
import guau.composeapp.generated.resources.pet_kinds
import guau.composeapp.generated.resources.pets
import guau.composeapp.generated.resources.register_admission
import guau.composeapp.generated.resources.save
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App2() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Compose: ${Greeting().greet()}"
                showImage = !showImage
            }) {
                Text(stringResource(Res.string.age))
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource(Res.drawable.compose_multiplatform),
                    null
                )
            }
        }
    }
}

val store = CoroutineScope(SupervisorJob()).createStore()

@Composable
fun App(
    finishCallback: (() -> Unit)? = null,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit
) {
    KoinContext {
        AppContent2(
            finishCallback = finishCallback,
            loginWithGoogle = loginWithGoogle,
            signOutWithGoogle = signOutWithGoogle
        )
    }
}

@Composable
private fun AppContent2(
    finishCallback: (() -> Unit)?,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit
) {
    GuauTheme {
        Navigator(
            screen = SplashScreen()
        )
        { navigator: Navigator ->
            SlideTransition(navigator)
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun AppContent(
    finishCallback: (() -> Unit)?,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit
) {
    PreComposeApp {
        GuauTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val navigator = rememberNavigator()
                val splashViewModel =
                    GetSplashViewModel()

                val loginViewModel = GetLoginViewModel()

                val signUpViewModel = GetSignUpViewModel()

                val appViewModel =
                    GetAppViewModel()

                val myVetsViewModel = GetMyVetsViewModel()

                val addPetViewModel = GetAddPetViewModel()

                val addCustomerViewModel = GetAddCustomerViewModel()

                val customerViewModel = GetCustomerViewModel()

                val petsViewModel = GetPetsViewModel()

                var showExitAlertDialog by rememberSaveable { mutableStateOf(false) }
                var showCancelProcessDialog by rememberSaveable { mutableStateOf(false) }
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

                val onClickAddActionButton: () -> Unit = {
                    when (title) {
                        ScreenEnum.Admissions -> {
                            navigator.navigate(
                                route = AppNavigationRoute.AdmissionTypeScreen.route
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

                val onBack: () -> Unit = {
                    when (title) {
                        ScreenEnum.SelectPetType -> {
                            showCancelProcessDialog = true
                        }

                        ScreenEnum.AddCustomer -> {
                            addCustomerViewModel.emptyValues()
                            navigator.popBackStack()
                        }

                        ScreenEnum.Pets -> {
                            petsViewModel.emptyValues()
                            navigator.popBackStack()
                        }

                        else -> {
                            navigator.popBackStack()
                        }
                    }
                }


                val onNextOnClick: () -> Unit = {
                    when (title) {
                        ScreenEnum.SelectPetType -> {
                            navigator.navigate(AppNavigationRoute.SelectBreedScreen.route)
                        }

                        ScreenEnum.SelectBreed -> {
                            navigator.navigate(AppNavigationRoute.PetDataScreen.route)
                        }

                        ScreenEnum.PetData -> {
                            navigator.navigate(AppNavigationRoute.SummaryPetScreen.route)
                        }

                        else -> {

                        }
                    }
                }

                val onSaveOnClick: () -> Unit = {
                    when (title) {
                        ScreenEnum.AddCustomer -> {
                            addCustomerViewModel.save()
                        }

                        ScreenEnum.SummaryPet -> {
                            addPetViewModel.save()
                        }

                        else -> {

                        }
                    }
                }

                val onSaveSuccess: () -> Unit = {
                    when (title) {
                        ScreenEnum.SummaryPet -> {
                            navigator.navigate(
                                route = AppNavigationRoute.PetsScreen.route, options = NavOptions(
                                    popUpTo = PopUpTo(
                                        route = AppNavigationRoute.SelectPetScreen.route,
                                        inclusive = false,
                                    ),
                                )
                            )
                        }

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

                BackHandler {
                    onBack()
                }

                Scaffold(
                    floatingActionButton = if ((getPlatformName() == PlatformConstants.ANDROID || getPlatformName() == PlatformConstants.JVM) && showAddActionButton) {
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
                                onBackOnClick = onBack,
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
                                customerViewModel = customerViewModel,
                                petsViewModel = petsViewModel,
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
                                onBack = onBack,
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
                                            navigator.navigate(
                                                route = AppNavigationRoute.PetsScreen.createRoute(
                                                    "${ScreenEnum.SelectPet}"
                                                )
                                            )
                                        }

                                        ScreenEnum.SelectPetType -> {
                                            navigator.navigate(route = AppNavigationRoute.SpeciesScreen.route)
                                        }

                                        ScreenEnum.SelectBreed -> {
                                            navigator.navigate(route = AppNavigationRoute.BreedsScreen.route)
                                        }

                                        ScreenEnum.PetData -> {
                                            navigator.navigate(
                                                route = AppNavigationRoute.CustomersScreen.createRoute(
                                                    "${ScreenEnum.PetData}"
                                                )
                                            )
                                        }

                                        ScreenEnum.AdmissionType -> {
                                            navigator.navigate(route = AppNavigationRoute.SelectPetScreen.route)
                                        }

                                        else -> {

                                        }
                                    }

                                },
                                addOnClick = onClickAddActionButton,
                                onSaveSuccess = onSaveSuccess
                            )
                        }
                        if (showBottomAction) {
                            Box(
                                Modifier.fillMaxWidth().height(80.dp).align(Alignment.BottomCenter)
                            ) {
                                if (showNextAction) {
                                    Button(
                                        onClick = onNextOnClick,
                                        modifier = Modifier.align(Alignment.TopEnd)
                                            .padding(end = 20.dp),
                                        enabled = enableNextAction
                                    ) {
                                        Text(text = stringResource(Res.string.next))
                                    }
                                }
                                if (showSaveAction) {
                                    Button(
                                        onClick = onSaveOnClick,
                                        modifier = Modifier.align(Alignment.TopEnd)
                                            .padding(end = 20.dp),
                                        enabled = enableSaveAction
                                    ) {
                                        Text(text = stringResource(Res.string.save))
                                    }
                                }
                            }
                        }

                    }
                }
                val showSignOffDialog by appViewModel.showSignOffDialog.collectAsState()
                TwoButtonDialog(
                    show = showSignOffDialog,
                    message = stringResource(Res.string.are_you_sure_you_want_to_log_out),
                    confirmButtonText = stringResource(Res.string.ok),
                    cancelButtonText = stringResource(Res.string.cancel),
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
                    message = stringResource(Res.string.are_you_sure_you_want_to_exit_the_application),
                    confirmButtonText = stringResource(Res.string.ok),
                    cancelButtonText = stringResource(Res.string.cancel),
                    confirmButton = {
                        showExitAlertDialog = false
                        if (finishCallback != null) {
                            finishCallback()
                        }
                    },
                    onDismissRequest = {
                        showExitAlertDialog = false
                    })

                TwoButtonDialog(
                    show = showCancelProcessDialog,
                    message = stringResource(Res.string.are_you_sure_you_want_to_cancel_the_registration_process),
                    confirmButtonText = stringResource(Res.string.ok),
                    cancelButtonText = stringResource(Res.string.cancel),
                    confirmButton = {
                        when (title) {
                            ScreenEnum.SelectPetType -> {
                                addPetViewModel.emptyValues()
                            }

                            else -> {
                            }
                        }
                        showCancelProcessDialog = false
                        navigator.popBackStack()
                    },
                    onDismissRequest = {
                        showCancelProcessDialog = false
                    })

                val showExitCenterDialog by appViewModel.showExitCenterDialog.collectAsState()
                TwoButtonDialog(
                    show = showExitCenterDialog,
                    message = stringResource(Res.string.are_you_sure_you_want_to_exit),
                    confirmButtonText = stringResource(Res.string.ok),
                    cancelButtonText = stringResource(Res.string.cancel),
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
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun getTitle(screenEnum: ScreenEnum): String {
    return when (screenEnum) {
        ScreenEnum.MyVets -> {
            stringResource(Res.string.my_vets)
        }

        ScreenEnum.Home -> {
            stringResource(Res.string.home)
        }

        ScreenEnum.Admissions -> {
            stringResource(Res.string.admissions)
        }


        ScreenEnum.Pets -> {
            stringResource(Res.string.pets)
        }

        ScreenEnum.SelectPetType, ScreenEnum.SelectBreed, ScreenEnum.PetData, ScreenEnum.SummaryPet -> {
            stringResource(Res.string.add_pet)
        }

        ScreenEnum.PetTypes -> {
            stringResource(Res.string.pet_kinds)
        }

        ScreenEnum.Breeds -> {
            stringResource(Res.string.breeds)
        }

        ScreenEnum.Customers -> {
            stringResource(Res.string.customers)
        }

        ScreenEnum.AddCustomer -> {
            stringResource(Res.string.customer_registry)
        }

        ScreenEnum.AdmissionType, ScreenEnum.SelectPet -> {
            stringResource(Res.string.register_admission)
        }

        else -> {
            ""
        }
    }
}