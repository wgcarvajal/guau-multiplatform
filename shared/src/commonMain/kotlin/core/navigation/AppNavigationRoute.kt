package core.navigation

sealed class AppNavigationRoute(val route: String) {
    object SplashScreen : AppNavigationRoute(route = "SplashScreen")

    object LoginScreen : AppNavigationRoute(route = "LoginScreen")

    object SignUpScreen : AppNavigationRoute(route = "SignUpScreen")

    object InitialScreen : AppNavigationRoute(route = "InitialScreen")

    object MyVetsScreen : AppNavigationRoute(route = "MyVetsScreen")

    object HomeScreen : AppNavigationRoute(route = "HomeScreen")

    object Admissions : AppNavigationRoute(route = "Admissions")

}