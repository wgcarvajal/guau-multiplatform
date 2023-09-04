package initialsetup.navigation

sealed class InitialSetupNavigationRoute(val route: String) {
    object InitialScreen : InitialSetupNavigationRoute(route = "InitialScreen")
    object MyVetsScreen : InitialSetupNavigationRoute(route = "MyVetsScreen")

}