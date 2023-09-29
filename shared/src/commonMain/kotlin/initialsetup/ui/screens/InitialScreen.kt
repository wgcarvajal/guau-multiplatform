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
import core.ui.constants.ScreenEnum
import core.ui.model.UiStructureProperties
import core.ui.screens.buttons.GeneralButton
import dev.icerock.moko.resources.compose.stringResource
import moe.tlaster.precompose.navigation.BackHandler


@Composable
fun InitialScreen(
    uiStructureProperties: UiStructureProperties,
    myVetsOnClick: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(false)
        uiStructureProperties.onShowExitCenter(false)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.onSetTitle(ScreenEnum.Initial)
    }

    BackHandler {
        onBack()
    }
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