package navigation

sealed class AppNavigationRoute(val route: String) {
    object SplashScreen : AppNavigationRoute(route = "SplashScreen")

    object LoginScreen : AppNavigationRoute(route = "LoginScreen")
}