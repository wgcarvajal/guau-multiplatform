package com.carpisoft.guau.customer.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.itemlist.ItemCustomer
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimpleWithSearch
import com.carpisoft.guau.core.ui.screens.search.SearchAndContentTemplateScreen
import com.carpisoft.guau.core.ui.screens.snackbars.DecoupledSnackBar
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.pet.ui.util.PetHelper
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.customers
import guau.composeapp.generated.resources.hide
import guau.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class CustomersScreen : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        val customerViewModel = koinViewModel<CustomerViewModel>()
        val petHelper = koinInject<PetHelper>()
        val loading by customerViewModel.loading.collectAsState()
        val loadingMore by customerViewModel.loadingMore.collectAsState()
        val customers by customerViewModel.customers.collectAsState()
        val showError by customerViewModel.showError.collectAsState()
        val searchText by customerViewModel.searchText.collectAsState()
        Screen(
            customers = customers,
            searchText = searchText,
            loading = loading,
            loadingMore = loadingMore,
            showError = showError,
            onLoadMore = {
                customerViewModel.onLoadMoreBreedData()
            },
            onBackOnClick = {
                customerViewModel.resetCustomers()
                navigator?.pop()
            },
            onValueChange = {
                customerViewModel.onValueSearchCustomer(it)
            },
            onEmptyOnClick = {
                customerViewModel.onValueSearchCustomer("")
            },
            addOnClick = {
                navigator?.push(item = AddCustomerScreen())
            },
            onSelectCustomer = {
                petHelper.setCustomerSelected(customerResp = it)
                navigator?.pop()
            }
        )
        TwoButtonDialog(
            show = showError,
            message = GetMessageErrorUseCase(errorUi = customerViewModel.getErrorUi() ?: ErrorUi()),
            confirmButtonText = stringResource(Res.string.retry),
            cancelButtonText = stringResource(Res.string.cancel),
            confirmButton = {
                customerViewModel.dismissErrorDialog()
                customerViewModel.loadCustomerData()
            },
            onDismissRequest = {
                customerViewModel.dismissErrorDialog()
                navigator?.pop()
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
        val errorMessage =
            GetMessageErrorUseCase(errorUi = customerViewModel.getErrorUi() ?: ErrorUi())
        val actionLabel = stringResource(Res.string.hide)
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

}

@Composable
private fun Screen(
    customers: List<CustomerResp>,
    searchText: String,
    loading: Boolean,
    loadingMore: Boolean,
    showError: Boolean,
    onLoadMore: () -> Unit,
    onBackOnClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onEmptyOnClick: () -> Unit,
    addOnClick: () -> Unit,
    onSelectCustomer: (CustomerResp) -> Unit
) {
    val state = rememberLazyListState()
    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        GuauScaffoldSimpleWithSearch(
            listState = state,
            title = stringResource(resource = Res.string.customers),
            searchText = searchText,
            loadingMore = loadingMore,
            showAddActionButton = true,
            onClickAddActionButton = addOnClick,
            onLoadMore = onLoadMore, onValueChange = onValueChange, onEmptyOnClick = onEmptyOnClick,
            onBack = onBackOnClick,
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
}

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
            title = stringResource(Res.string.customers),
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
        confirmButtonText = stringResource(Res.string.retry),
        cancelButtonText = stringResource(Res.string.cancel),
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
    val actionLabel = stringResource(Res.string.hide)
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