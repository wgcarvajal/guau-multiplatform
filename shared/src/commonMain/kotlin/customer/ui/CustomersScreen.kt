package customer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import core.ui.constants.ScreenEnum
import core.ui.model.UiStructureProperties

@Composable
fun CustomersScreen(
    uiStructureProperties: UiStructureProperties
) {
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.Customers)
        uiStructureProperties.showAddActionButton(true)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.onShowActionBottom(false)
    }
}