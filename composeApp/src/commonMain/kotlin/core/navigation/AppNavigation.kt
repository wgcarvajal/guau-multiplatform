package core.navigation

import SplashScreen
import admission.ui.screens.AdmissionRegisterViewModel
import admission.ui.screens.AdmissionScreen
import admission.ui.screens.AdmissionTypeScreen
import admission.ui.screens.SelectPetScreen
import androidx.compose.runtime.Composable
import core.ui.constants.ScreenEnum
import core.ui.model.UiStructureProperties
import customer.ui.AddCustomerScreen
import customer.ui.AddCustomerViewModel
import customer.ui.CustomerViewModel
import customer.ui.CustomersScreen
import home.ui.HomeScreen
import initialsetup.ui.screens.InitialScreen
import initialsetup.ui.screens.MyVetsScreen
import initialsetup.ui.screens.MyVetsViewModel
import login.ui.screens.LoginScreen
import login.ui.screens.LoginViewModel
import login.ui.screens.SignUpScreen
import login.ui.screens.SignUpViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import pet.ui.screens.AddPetViewModel
import pet.ui.screens.BreedsScreen
import pet.ui.screens.PetDataScreen
import pet.ui.screens.PetsScreen
import pet.ui.screens.PetsViewModel
import pet.ui.screens.SelectBreedScreen
import pet.ui.screens.SelectPetTypeScreen
import pet.ui.screens.SpeciesScreens
import pet.ui.screens.SummaryPetScreen
import splash.ui.screens.SplashViewModel
import ui.AppViewModel

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
    admissionRegisterViewModel: AdmissionRegisterViewModel,
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
                admissionRegisterViewModel = admissionRegisterViewModel,
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
                                admissionRegisterViewModel.selectedPet(pet)
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
                admissionRegisterViewModel = admissionRegisterViewModel,
                selectAdmissionType = onSelectAction
            )
        }
    }
}
