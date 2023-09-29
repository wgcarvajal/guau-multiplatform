package core.ui.model

import core.ui.constants.ScreenEnum

data class UiStructureProperties(
    val onShowTopBar: (Boolean) -> Unit,
    val onShowExitCenter: (Boolean) -> Unit,
    val onShowBottomBar: (Boolean) -> Unit,
    val showActionNavigation: (Boolean) -> Unit,
    val showActionAccountOptions: (Boolean) -> Unit,
    val showAddActionButton: (Boolean) -> Unit,
    val showActionNext: (Boolean) -> Unit,
    val onEnabledNextAction: (Boolean) -> Unit,
    val onSetTitle: (ScreenEnum) -> Unit
)
