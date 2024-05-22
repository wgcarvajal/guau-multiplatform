package com.carpisoft.guau.core.ui.model

import com.carpisoft.guau.core.ui.constants.ScreenEnum

data class UiStructureProperties(
    val onShowTopBar: (Boolean) -> Unit,
    val onShowExitCenter: (Boolean) -> Unit,
    val onShowBottomBar: (Boolean) -> Unit,
    val showActionNavigation: (Boolean) -> Unit,
    val showActionAccountOptions: (Boolean) -> Unit,
    val showAddActionButton: (Boolean) -> Unit,
    val onShowActionBottom: (Boolean) -> Unit,
    val showActionNext: (Boolean) -> Unit,
    val onEnabledNextAction: (Boolean) -> Unit,
    val onShowSaveAction: (Boolean) -> Unit,
    val onEnabledSaveAction: (Boolean) -> Unit,
    val onSetTitle: (ScreenEnum) -> Unit
)
