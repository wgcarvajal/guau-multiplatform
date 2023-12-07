package admission.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import core.ui.constants.ScreenEnum
import core.ui.model.UiStructureProperties

@Composable
fun AdmissionScreen(uiStructureProperties: UiStructureProperties) {

    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showAddActionButton(true)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.Admissions)
        uiStructureProperties.showActionAccountOptions(true)
        uiStructureProperties.showActionNext(false)
        uiStructureProperties.onEnabledNextAction(false)
    }

}