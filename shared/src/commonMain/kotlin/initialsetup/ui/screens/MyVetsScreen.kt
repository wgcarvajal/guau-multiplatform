package initialsetup.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.myapplication.SharedRes
import core.domain.usecase.GetMessageErrorUseCase
import core.ui.model.ErrorUi
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.loading.SimpleLoading
import dev.icerock.moko.resources.compose.stringResource
import initialsetup.domain.model.EmployeeResp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun MyVetsScreen(
    myVetsViewModel: MyVetsViewModel,
    showNavigation: (Boolean) -> Unit,
    onShowTopBar:(Boolean)->Unit,
    showFloatActionButton: (Boolean, () -> Unit) -> Unit,
    onSetTitle: (String) -> Unit,
    onBackOnClick: () -> Unit
) {
    var myVetsString: String = stringResource(SharedRes.strings.my_vets)
    LaunchedEffect(key1 = 1) {
        onShowTopBar(true)
        onSetTitle(myVetsString)
        showNavigation(true)
    }
    val loading by myVetsViewModel.loading.collectAsState()
    val isEmpty by myVetsViewModel.isEmpty.collectAsState()
    val showError by myVetsViewModel.showError.collectAsState()
    val list by myVetsViewModel.list.collectAsState()

    ScreenPortrait(
        loading = loading,
        isEmpty = isEmpty,
        showError = showError,
        showFloatActionButton = showFloatActionButton,
        list = list
    )
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
}


@Composable
private fun ScreenPortrait(
    loading: Boolean,
    isEmpty: Boolean,
    showError: Boolean,
    showFloatActionButton: (Boolean, () -> Unit) -> Unit,
    list: List<EmployeeResp>
) {
    if (loading) {
        SimpleLoading()
    } else if (isEmpty) {
        Box(modifier = Modifier.fillMaxSize()) {
            showFloatActionButton(true) {
            }
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(
                    SharedRes.strings.you_do_not_have_any_veterinary_created
                ),
                modifier = Modifier.fillMaxWidth().align(Alignment.Center)
            )
        }
    } else if (!showError) {

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()
                .background(Color.White)
                .padding(10.dp)) {
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
                                .height(100.dp)
                        ) {
                            KamelImage(
                                modifier = Modifier
                                    .width(100.dp)
                                    .fillMaxHeight()
                                    .background(Color.Gray),
                                resource = asyncPainterResource(data = employeeResp.centerResp.image?:""),
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