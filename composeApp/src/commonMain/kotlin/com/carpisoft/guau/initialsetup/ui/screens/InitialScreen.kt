package com.carpisoft.guau.initialsetup.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.buttons.GeneralButton
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimple
import com.carpisoft.guau.login.ui.states.SignOutWithGoogleHandler
import com.carpisoft.guau.login.ui.states.SocialLoginAction
import com.carpisoft.guau.socialLogin
import com.carpisoft.guau.splash.ui.screens.SplashScreen
import com.carpisoft.guau.ui.AppViewModel
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.my_jobs
import guau.composeapp.generated.resources.my_vets
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class InitialScreen : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        val appViewModel = koinViewModel<AppViewModel>()
        val successSignOff by appViewModel.successSignOff.collectAsState()
        Screen(myVetsOnClick = {
            navigator?.push(item = MyVetsScreen())
        })
        SignOutWithGoogleHandler {
            appViewModel.signOff()
        }
        LaunchedEffect(successSignOff) {
            if (successSignOff) {
                appViewModel.resetSuccessSignOff()
                navigator?.replace(item = SplashScreen())
            }
        }
    }

}

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
    Screen(myVetsOnClick = myVetsOnClick)
}

@Composable
private fun Screen(myVetsOnClick: () -> Unit) {
    GuauScaffoldSimple(
        title = "",
        showAccountOptions = true,
        signOffOnClick = { socialLogin.send(action = SocialLoginAction.GoSignOutWithGoogle()) },
        onExitVet = {},
        onBack = {}
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize().padding(paddingValues = paddingValues)
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
}