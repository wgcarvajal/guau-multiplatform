package customer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.SharedRes
import core.domain.usecase.GetMessageErrorUseCase
import core.ui.constants.ScreenEnum
import core.ui.model.ErrorUi
import core.ui.model.UiStructureProperties
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.itemlist.ItemCustomer
import core.ui.screens.loading.SimpleLoading
import core.ui.screens.search.SearchAndContentTemplateScreen
import core.ui.screens.snackbars.DecoupledSnackBar
import customer.domain.model.CustomerResp
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomersScreen(
    uiStructureProperties: UiStructureProperties,
    customerViewModel: CustomerViewModel,
    onBackOnClick: () -> Unit,
    onSelectCustomer: ((CustomerResp) -> Unit)? = null,
    addOnClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(false)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.Customers)
        uiStructureProperties.showAddActionButton(true)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.showActionNext(false)
        uiStructureProperties.onEnabledNextAction(false)
        uiStructureProperties.onShowActionBottom(false)
        uiStructureProperties.onShowSaveAction(false)
        uiStructureProperties.onEnabledSaveAction(false)
    }
    val loading by customerViewModel.loading.collectAsState()
    val loadingMore by customerViewModel.loadingMore.collectAsState()
    val customers by customerViewModel.customers.collectAsState()
    val showError by customerViewModel.showError.collectAsState()
    val searchText by customerViewModel.searchText.collectAsState()
    var activeSearch by rememberSaveable { mutableStateOf(false) }
    val state = rememberLazyListState()
    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        SearchAndContentTemplateScreen(
            title = stringResource(SharedRes.strings.customers),
            listState = state,
            searchText = searchText,
            activeSearch = activeSearch,
            loadingMore = loadingMore,
            keyboardController = keyboardController,
            focusManager = focusManager,
            showAdd = true,
            onLoadMore = { customerViewModel.onLoadMoreBreedData() },
            onBackOnClick = {
                customerViewModel.resetCustomers()
                onBackOnClick()
            },
            searchOnClick = { activeSearch = true },
            onValueChange = { customerViewModel.onValueSearchCustomer(it) },
            onEmptyOnClick = { customerViewModel.onValueSearchCustomer("") },
            onCloseOnClick = { activeSearch = false },
            addOnClick = addOnClick
        ) {
            LazyColumn(
                state = state,
                modifier = Modifier.fillMaxSize()
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(customers) { customer ->
                    ItemCustomer(
                        identificationType = customer.identificationType.name,
                        identification = customer.identification,
                        name = "${customer.name} ${customer.lastName}",
                        onClick = {
                            if (onSelectCustomer != null) {
                                onSelectCustomer(customer)
                            }
                        }
                    )
                }
            }
        }
    }
    TwoButtonDialog(
        show = showError,
        message = GetMessageErrorUseCase(errorUi = customerViewModel.getErrorUi() ?: ErrorUi()),
        confirmButtonText = stringResource(SharedRes.strings.retry),
        cancelButtonText = stringResource(SharedRes.strings.cancel),
        confirmButton = {
            customerViewModel.dismissErrorDialog()
            customerViewModel.loadCustomerData()
        },
        onDismissRequest = {
            customerViewModel.dismissErrorDialog()
            onBackOnClick()
        })

    LaunchedEffect(key1 = 1) {
        customerViewModel.loadCustomerData()
    }
    val snackBarHostState = remember { SnackbarHostState() }
    DecoupledSnackBar(
        snackBarHostState = snackBarHostState,
        containerColor = Color.Red,
        messageColor = Color.White,
        actionColor = Color.White
    )
    val showErrorSnackBar by customerViewModel.showErrorSnackBar.collectAsState()
    val errorMessage = GetMessageErrorUseCase(errorUi = customerViewModel.getErrorUi() ?: ErrorUi())
    val actionLabel = stringResource(SharedRes.strings.hide)
    LaunchedEffect(key1 = showErrorSnackBar) {
        if (showErrorSnackBar) {
            snackBarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
            customerViewModel.resetShowErrorSnackBar()
        }
    }
}