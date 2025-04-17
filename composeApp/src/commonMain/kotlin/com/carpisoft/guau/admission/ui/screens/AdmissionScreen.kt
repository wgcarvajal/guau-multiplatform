package com.carpisoft.guau.admission.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimple
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.admissions
import org.jetbrains.compose.resources.stringResource

class AdmissionScreen : Screen {
    companion object {
        const val TAG = "AdmissionScreen"
    }

    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        Screen(
            onClickAddActionButton = { navigator?.push(item = AdmissionTypeScreen()) },
            onBack = {
                navigator?.pop()
            })
    }

    @Composable
    private fun Screen(onClickAddActionButton: () -> Unit, onBack: () -> Unit) {
        GuauScaffoldSimple(
            title = stringResource(Res.string.admissions),
            showAddActionButton = true,
            onClickAddActionButton = onClickAddActionButton,
            onBack = onBack
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues))
        }
    }

}

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