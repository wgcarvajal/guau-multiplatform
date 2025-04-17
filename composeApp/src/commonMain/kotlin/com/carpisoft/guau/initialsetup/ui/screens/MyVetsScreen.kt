package com.carpisoft.guau.initialsetup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimple
import com.carpisoft.guau.initialsetup.domain.model.EmployeeResp
import com.carpisoft.guau.login.ui.states.SignOutWithGoogleHandler
import com.carpisoft.guau.login.ui.states.SocialLoginAction
import com.carpisoft.guau.main.ui.screens.MainSplashScreen
import com.carpisoft.guau.socialLogin
import com.carpisoft.guau.splash.ui.screens.SplashScreen
import com.carpisoft.guau.ui.AppViewModel
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.my_vets
import guau.composeapp.generated.resources.retry
import guau.composeapp.generated.resources.you_do_not_have_any_veterinary_created
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class MyVetsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        val myVetsViewModel = koinViewModel<MyVetsViewModel>()
        val appViewModel = koinViewModel<AppViewModel>()
        val successSignOff by appViewModel.successSignOff.collectAsState()
        val loading by myVetsViewModel.loading.collectAsState()
        val isEmpty by myVetsViewModel.isEmpty.collectAsState()
        val showError by myVetsViewModel.showError.collectAsState()
        val list by myVetsViewModel.list.collectAsState()
        val onBack: () -> Unit = {
            navigator?.pop()
        }
        Screen(
            loading = loading,
            isEmpty = isEmpty,
            showError = showError,
            changeShowAddActionButton = {},
            signOffOnClick = {
                socialLogin.send(action = SocialLoginAction.GoSignOutWithGoogle())
            },
            onBack = {
                navigator?.pop()
            },
            list = list,
        ) { employee ->
            myVetsViewModel.selectVet(employeeResp = employee)
        }
        LaunchedEffect(key1 = 1) {
            myVetsViewModel.showMyVets()
        }

        TwoButtonDialog(
            show = showError,
            message = GetMessageErrorUseCase(errorUi = myVetsViewModel.getErrorUi() ?: ErrorUi()),
            confirmButtonText = stringResource(Res.string.retry),
            cancelButtonText = stringResource(Res.string.cancel),
            confirmButton = {
                myVetsViewModel.dismissErrorDialog()
                myVetsViewModel.showMyVets()
            },
            onDismissRequest = {
                myVetsViewModel.dismissErrorDialog()
                onBack()
            })

        val goHome by myVetsViewModel.goHome.collectAsState()
        if (goHome) {
            myVetsViewModel.resetGoHome()
            navigator?.replace(item = MainSplashScreen())
        }
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
fun MyVetsScreen(
    uiStructureProperties: UiStructureProperties,
    myVetsViewModel: MyVetsViewModel,
    onBackOnClick: () -> Unit,
    onGoHome: () -> Unit
) {

    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(false)
        uiStructureProperties.onSetTitle(ScreenEnum.MyVets)
    }

    var showAddButton by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = showAddButton) {
        uiStructureProperties.showAddActionButton(showAddButton)
    }

    val loading by myVetsViewModel.loading.collectAsState()
    val isEmpty by myVetsViewModel.isEmpty.collectAsState()
    val showError by myVetsViewModel.showError.collectAsState()
    val list by myVetsViewModel.list.collectAsState()

    Screen(
        loading = loading,
        isEmpty = isEmpty,
        showError = showError,
        changeShowAddActionButton = {
            showAddButton = it
        },
        signOffOnClick = {},
        onBack = {},
        list = list
    ) { employee ->
        myVetsViewModel.selectVet(employeeResp = employee)
    }

    LaunchedEffect(key1 = 1) {
        myVetsViewModel.showMyVets()
    }

    TwoButtonDialog(
        show = showError,
        message = GetMessageErrorUseCase(errorUi = myVetsViewModel.getErrorUi() ?: ErrorUi()),
        confirmButtonText = stringResource(Res.string.retry),
        cancelButtonText = stringResource(Res.string.cancel),
        confirmButton = {
            myVetsViewModel.dismissErrorDialog()
            myVetsViewModel.showMyVets()
        },
        onDismissRequest = {
            myVetsViewModel.dismissErrorDialog()
            onBackOnClick()
        })

    val goHome by myVetsViewModel.goHome.collectAsState()
    if (goHome) {
        myVetsViewModel.resetGoHome()
        onGoHome()
    }
}

@Composable
private fun Screen(
    loading: Boolean,
    isEmpty: Boolean,
    showError: Boolean,
    changeShowAddActionButton: (Boolean) -> Unit,
    signOffOnClick: () -> Unit,
    onBack: () -> Unit,
    list: List<EmployeeResp>,
    onClick: (EmployeeResp) -> Unit
) {
    var showAddActionButton: Boolean by rememberSaveable { mutableStateOf(value = false) }
    GuauScaffoldSimple(
        title = stringResource(resource = Res.string.my_vets),
        showAddActionButton = showAddActionButton,
        showAccountOptions = true,
        onClickAddActionButton = {},
        signOffOnClick = signOffOnClick,
        onExitVet = {},
        onBack = onBack
    ) { paddingValues ->
        if (loading) {
            showAddActionButton = false
            SimpleLoading()
        }

        if (!showError && isEmpty) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
                showAddActionButton = true
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(
                        Res.string.you_do_not_have_any_veterinary_created
                    ),
                    modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                )
            }
        }
        if (!showError && !isEmpty) {
            showAddActionButton = false
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp)
                ) {
                    items(list) { employeeResp ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(100.dp).clickable {
                                        onClick(employeeResp)
                                    }
                            ) {
                                KamelImage(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .fillMaxHeight()
                                        .background(Color.Gray),
                                    resource = asyncPainterResource(
                                        data = employeeResp.centerResp.image ?: ""
                                    ),
                                    contentDescription = null,
                                    onFailure = {

                                    },
                                    colorFilter = ColorFilter.tint(color = Color.White),
                                    contentScale = ContentScale.FillBounds
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(start = 5.dp, end = 5.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier.align(Alignment.End),
                                        imageVector = Icons.Filled.ArrowForwardIos,
                                        contentDescription = null
                                    )
                                    Text(
                                        modifier = Modifier.weight(1.2f),
                                        text = employeeResp.centerResp.name
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = employeeResp.centerResp.address
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = employeeResp.centerResp.phone
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}