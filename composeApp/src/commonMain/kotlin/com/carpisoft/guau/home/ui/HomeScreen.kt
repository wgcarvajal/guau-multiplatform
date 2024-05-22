package com.carpisoft.guau.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.itemlist.ItemHome
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.admissions
import guau.composeapp.generated.resources.admit
import guau.composeapp.generated.resources.appointment
import guau.composeapp.generated.resources.calendar
import guau.composeapp.generated.resources.vaccine
import guau.composeapp.generated.resources.vaccines
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import com.carpisoft.guau.ui.theme.NavyBlue
import com.carpisoft.guau.ui.theme.Orange
import com.carpisoft.guau.ui.theme.Purple

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(
    uiStructureProperties: UiStructureProperties,
    onAdmission: () -> Unit
) {

    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(true)
        uiStructureProperties.showActionNavigation(false)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.Home)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionAccountOptions(true)
        uiStructureProperties.showActionNext(false)
        uiStructureProperties.onEnabledNextAction(false)
    }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize()
            .padding(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
        columns = GridCells.Fixed(count = 2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            ItemHome(
                text = stringResource(Res.string.admissions),
                backgroundColor = NavyBlue,
                imageResource = Res.drawable.admit,
                onClick = onAdmission
            )
        }
        item {
            ItemHome(
                text = stringResource(Res.string.appointment),
                backgroundColor = Orange,
                imageResource = Res.drawable.calendar,
                onClick = {}
            )
        }
        item {
            ItemHome(
                text = stringResource(Res.string.vaccines),
                backgroundColor = Purple,
                imageResource = Res.drawable.vaccine,
                onClick = {}
            )
        }
    }
}