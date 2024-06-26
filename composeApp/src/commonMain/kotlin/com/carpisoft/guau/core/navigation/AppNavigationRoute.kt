package com.carpisoft.guau.core.navigation

sealed class AppNavigationRoute(val route: String) {
    object SplashScreen : AppNavigationRoute(route = "com.carpisoft.guau.splash.ui.screens.SplashScreen")

    object LoginScreen : AppNavigationRoute(route = "LoginScreen")

    object SignUpScreen : AppNavigationRoute(route = "SignUpScreen")

    object InitialScreen : AppNavigationRoute(route = "InitialScreen")

    object MyVetsScreen : AppNavigationRoute(route = "MyVetsScreen")

    object HomeScreen : AppNavigationRoute(route = "HomeScreen")

    object AdmissionScreen : AppNavigationRoute(route = "AdmissionScreen")

    object SelectPetScreen : AppNavigationRoute(route = "SelectPetScreen")

    object PetsScreen : AppNavigationRoute(route = "PetsScreen/{caller}") {
        fun createRoute(caller: String) = "PetsScreen/$caller"
    }

    object SelectPetTypeScreen : AppNavigationRoute(route = "SelectPetTypeScreen")

    object SelectBreedScreen : AppNavigationRoute(route = "SelectBreedScreen")

    object SpeciesScreen : AppNavigationRoute(route = "SpeciesScreen")

    object BreedsScreen : AppNavigationRoute(route = "BreedsScreen")

    object PetDataScreen : AppNavigationRoute(route = "PetDataScreen")

    object CustomersScreen : AppNavigationRoute(route = "CustomersScreen/{caller}") {
        fun createRoute(caller: String) = "CustomersScreen/$caller"
    }

    object AddCustomerScreen : AppNavigationRoute(route = "AddCustomerScreen")

    object SummaryPetScreen : AppNavigationRoute(route = "SummaryPetScreen")

    object AdmissionTypeScreen : AppNavigationRoute(route = "AdmissionTypeScreen")
}