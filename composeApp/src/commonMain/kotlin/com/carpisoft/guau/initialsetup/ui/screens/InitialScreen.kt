package com.carpisoft.guau.initialsetup.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.buttons.GeneralButton
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.my_jobs
import guau.composeapp.generated.resources.my_vets
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource


@Composable
fun InitialScreen(
    uiStructureProperties: UiStructureProperties,
    myVetsOnClick: () -> Unit
) {
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(false)
        uiStructureProperties.onShowExitCenter(false)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.onSetTitle(ScreenEnum.Initial)
    }
    ScreenPortrait(myVetsOnClick = myVetsOnClick)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ScreenPortrait(myVetsOnClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            GeneralButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.my_vets),
                enabled = true,
                onClick = myVetsOnClick
            )
            GeneralButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.my_jobs),
                enabled = true,
                onClick = {})
        }

    }
}