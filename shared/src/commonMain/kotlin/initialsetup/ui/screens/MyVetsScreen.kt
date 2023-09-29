package initialsetup.ui.screens

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
import com.carpisoft.guau.SharedRes
import core.domain.usecase.GetMessageErrorUseCase
import core.ui.constants.ScreenEnum
import core.ui.model.ErrorUi
import core.ui.model.UiStructureProperties
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.loading.SimpleLoading
import dev.icerock.moko.resources.compose.stringResource
import initialsetup.domain.model.EmployeeResp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

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

    ScreenPortrait(
        loading = loading,
        isEmpty = isEmpty,
        showError = showError,
        changeShowAddActionButton = {
            showAddButton = it
        },
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
        confirmButtonText = stringResource(SharedRes.strings.retry),
        cancelButtonText = stringResource(SharedRes.strings.cancel),
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
private fun ScreenPortrait(
    loading: Boolean,
    isEmpty: Boolean,
    showError: Boolean,
    changeShowAddActionButton: (Boolean) -> Unit,
    list: List<EmployeeResp>,
    onClick: (EmployeeResp) -> Unit
) {
    if (loading) {
        changeShowAddActionButton(false)
        SimpleLoading()
    } else if (isEmpty) {
        Box(modifier = Modifier.fillMaxSize()) {
            changeShowAddActionButton(true)
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(
                    SharedRes.strings.you_do_not_have_any_veterinary_created
                ),
                modifier = Modifier.fillMaxWidth().align(Alignment.Center)
            )
        }
    } else if (!showError) {
        changeShowAddActionButton(false)
        Box(modifier = Modifier.fillMaxSize()) {
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