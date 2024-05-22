package com.carpisoft.guau.core.navigation

import androidx.compose.runtime.Composable
import com.carpisoft.guau.admission.ui.screens.AdmissionScreen
import com.carpisoft.guau.admission.ui.screens.AdmissionTypeScreen
import com.carpisoft.guau.admission.ui.screens.SelectPetScreen
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.customer.ui.AddCustomerScreen
import com.carpisoft.guau.customer.ui.AddCustomerViewModel
import com.carpisoft.guau.customer.ui.CustomerViewModel
import com.carpisoft.guau.customer.ui.CustomersScreen
import com.carpisoft.guau.home.ui.HomeScreen
import com.carpisoft.guau.initialsetup.ui.screens.InitialScreen
import com.carpisoft.guau.initialsetup.ui.screens.MyVetsScreen
import com.carpisoft.guau.initialsetup.ui.screens.MyVetsViewModel
import com.carpisoft.guau.login.ui.screens.LoginScreen
import com.carpisoft.guau.login.ui.screens.LoginViewModel
import com.carpisoft.guau.login.ui.screens.SignUpScreen
import com.carpisoft.guau.login.ui.screens.SignUpViewModel
import com.carpisoft.guau.pet.ui.screens.AddPetViewModel
import com.carpisoft.guau.pet.ui.screens.BreedsScreen
import com.carpisoft.guau.pet.ui.screens.PetDataScreen
import com.carpisoft.guau.pet.ui.screens.PetsScreen
import com.carpisoft.guau.pet.ui.screens.PetsViewModel
import com.carpisoft.guau.pet.ui.screens.SelectBreedScreen
import com.carpisoft.guau.pet.ui.screens.SelectPetTypeScreen
import com.carpisoft.guau.pet.ui.screens.SpeciesScreens
import com.carpisoft.guau.pet.ui.screens.SummaryPetScreen
import com.carpisoft.guau.splash.ui.screens.SplashScreen
import com.carpisoft.guau.splash.ui.screens.SplashViewModel
import com.carpisoft.guau.ui.AppViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path

@Composable
fun AppNavigation(
    navigator: Navigator,
    uiStructureProperties: UiStructureProperties,
    appViewModel: AppViewModel,
    splashViewModel: SplashViewModel,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    myVetsViewModel: MyVetsViewModel,
    addPetViewModel: AddPetViewModel,
    addCustomerViewModel: AddCustomerViewModel,
    customerViewModel: CustomerViewModel,
    petsViewModel: PetsViewModel,
    launchLogin: () -> Unit,
    launchInitialSetup: () -> Unit,
    launchSignUp: () -> Unit,
    launchMyVets: () -> Unit,
    onBack: () -> Unit,
    launchHome: () -> Unit,
    loginWithGoogle: () -> Unit,
    onAdmission: () -> Unit,
    onSelectAction: () -> Unit,
    addOnClick: () -> Unit,
    onSaveSuccess: () -> Unit,
) {
    NavHost(
        navigator = navigator,
        initialRoute = AppNavigationRoute.SplashScreen.route,
    ) {
        scene(route = AppNavigationRoute.SplashScreen.route) {
            appViewModel.currentScreenRoute = AppNavigationRoute.SplashScreen.route
            SplashScreen(
                uiStructureProperties = uiStructureProperties,
                splashViewModel = splashViewModel,
                launchLogin = launchLogin,
                launchHome = launchHome,
                launchInitialSetup = launchInitialSetup
            )
        }

        scene(route = AppNavigationRoute.LoginScreen.route) {
            appViewModel.currentScreenRoute = AppNavigationRoute.LoginScreen.route
            LoginScreen(
                uiStructureProperties = uiStructureProperties,
                loginViewModel = loginViewModel,
                loginSuccess = launchInitialSetup,
                onClickSignUp = launchSignUp,
                loginWithGoogle = loginWithGoogle
            )
        }

        scene(route = AppNavigationRoute.SignUpScreen.route) {
            SignUpScreen(
                uiStructureProperties = uiStructureProperties,
                signUpViewModel = signUpViewModel,
                onClickLink = onBack
            )
        }

        scene(route = AppNavigationRoute.InitialScreen.route)
        {
            appViewModel.currentScreenRoute = AppNavigationRoute.InitialScreen.route
            InitialScreen(
                uiStructureProperties = uiStructureProperties,
                myVetsOnClick = launchMyVets
            )
        }

        scene(route = AppNavigationRoute.MyVetsScreen.route)
        {
            MyVetsScreen(
                uiStructureProperties = uiStructureProperties,
                myVetsViewModel = myVetsViewModel,
                onBackOnClick = onBack,
                onGoHome = launchHome
            )
        }

        scene(route = AppNavigationRoute.HomeScreen.route)
        {
            appViewModel.currentScreenRoute = AppNavigationRoute.HomeScreen.route
            HomeScreen(
                uiStructureProperties = uiStructureProperties,
                onAdmission = onAdmission
            )
        }

        scene(route = AppNavigationRoute.AdmissionScreen.route)
        {
            AdmissionScreen(uiStructureProperties = uiStructureProperties)
        }

        scene(route = AppNavigationRoute.SelectPetScreen.route)
        {
            SelectPetScreen(
                uiStructureProperties = uiStructureProperties,
                onSelectAction = onSelectAction
            )
        }

        scene(route = AppNavigationRoute.PetsScreen.route)
        { backStackEntry ->
            val caller: String? = backStackEntry.path("caller")
            PetsScreen(
                uiStructureProperties = uiStructureProperties,
                petsViewModel = petsViewModel,
                onBackOnClick = onBack,
                onSelectPet = caller?.let {
                    when (it) {
                        "${ScreenEnum.SelectPet}" -> {
                            { pet ->
                                //admissionRegisterViewModel.selectedPet(pet)
                                onBack()
                            }
                        }

                        else -> {
                            null
                        }
                    }
                },
                addOnClick = addOnClick
            )
        }

        scene(route = AppNavigationRoute.SelectPetTypeScreen.route)
        {
            SelectPetTypeScreen(
                uiStructureProperties = uiStructureProperties,
                addPetViewModel = addPetViewModel,
                selectOnClick = onSelectAction,
                onBackOnClick = onBack
            )
        }

        scene(route = AppNavigationRoute.SpeciesScreen.route)
        {
            SpeciesScreens(
                uiStructureProperties = uiStructureProperties,
                addPetViewModel = addPetViewModel,
                onBackOnClick = onBack
            )
        }
        scene(route = AppNavigationRoute.SelectBreedScreen.route)
        {
            SelectBreedScreen(
                uiStructureProperties = uiStructureProperties,
                addPetViewModel = addPetViewModel,
                selectOnClick = onSelectAction
            )
        }

        scene(route = AppNavigationRoute.BreedsScreen.route)
        {
            BreedsScreen(
                uiStructureProperties = uiStructureProperties,
                addPetViewModel = addPetViewModel,
                onBackOnClick = onBack
            )
        }
        scene(route = AppNavigationRoute.PetDataScreen.route)
        {
            PetDataScreen(
                uiStructureProperties = uiStructureProperties,
                addPetViewModel = addPetViewModel,
                selectOnClick = onSelectAction,
                onBackOnClick = onBack
            )
        }

        scene(route = AppNavigationRoute.CustomersScreen.route)
        { backStackEntry ->
            val caller: String? = backStackEntry.path("caller")

            CustomersScreen(
                uiStructureProperties = uiStructureProperties,
                customerViewModel = customerViewModel,
                onBackOnClick = onBack,
                onSelectCustomer = caller?.let {
                    when (it) {
                        "${ScreenEnum.PetData}" -> {
                            { customer ->
                                addPetViewModel.selectedCustomer(customer)
                                onBack()
                            }
                        }

                        else -> {
                            null
                        }
                    }
                },
                addOnClick = addOnClick
            )
        }

        scene(route = AppNavigationRoute.AddCustomerScreen.route)
        {
            AddCustomerScreen(
                uiStructureProperties = uiStructureProperties,
                addCustomerViewModel = addCustomerViewModel,
                onBackOnClick = onBack
            )
        }

        scene(route = AppNavigationRoute.SummaryPetScreen.route)
        {
            SummaryPetScreen(
                uiStructureProperties = uiStructureProperties,
                addPetViewModel = addPetViewModel,
                onSaveSuccess = onSaveSuccess
            )
        }
        scene(route = AppNavigationRoute.AdmissionTypeScreen.route)
        {
            AdmissionTypeScreen(
                uiStructureProperties = uiStructureProperties,
                selectAdmissionType = onSelectAction
            )
        }
    }
}
