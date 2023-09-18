package initialsetup.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.carpisoft.guau.SharedRes
import core.ui.screens.buttons.GeneralButton
import dev.icerock.moko.resources.compose.stringResource
import moe.tlaster.precompose.navigation.BackHandler


@Composable
fun InitialScreen(
    showNavigation: (Boolean) -> Unit,
    onShowTopBar:(Boolean)->Unit,
    onShowExitCenter: (Boolean) -> Unit,
    onShowBottomBar:(Boolean)->Unit,
    myVetsOnClick: () -> Unit,
    showFloatActionButton: (Boolean, () -> Unit) -> Unit,
    onSetTitle: (String) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(key1 = 1) {
        onShowTopBar(true)
        onShowBottomBar(false)
        onSetTitle("")
        showNavigation(false)
        onShowExitCenter(false)
    }
    showFloatActionButton(false) {}
    BackHandler {
        onBack()
    }
    /*val myVetsOnClick = {
        navController.navigate(InitialSetupAppScreen.MyVetsScreen.route)
    }*/
    ScreenPortrait(myVetsOnClick = myVetsOnClick)
}

@Composable
private fun ScreenPortrait(myVetsOnClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            GeneralButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(SharedRes.strings.my_vets),
                enabled = true,
                onClick = myVetsOnClick
            )
            GeneralButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(SharedRes.strings.my_jobs),
                enabled = true,
                onClick = {})
        }

    }
}