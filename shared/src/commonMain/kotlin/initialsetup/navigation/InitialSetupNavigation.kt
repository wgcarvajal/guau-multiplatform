package initialsetup.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun InitialSetupNavigation(
    navigator: Navigator,
    showNavigation: (Boolean) -> Unit,
    myVetsOnClick: () -> Unit,
    showFloatActionButton: (Boolean, () -> Unit) -> Unit,
    onSetTitle: (String) -> Unit,
    onBack: () -> Unit
) {
    NavHost(
        navigator = navigator,
        initialRoute = InitialSetupNavigationRoute.InitialScreen.route
    ) {
        scene(route = InitialSetupNavigationRoute.InitialScreen.route)
        {

        }
        scene(route = InitialSetupNavigationRoute.MyVetsScreen.route)
        {

        }
    }
}