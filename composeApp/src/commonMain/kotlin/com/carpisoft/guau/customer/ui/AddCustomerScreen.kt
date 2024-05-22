package com.carpisoft.guau.customer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.dialogs.OneButtonDialog
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.snackbars.DecoupledSnackBar
import com.carpisoft.guau.core.ui.screens.textfields.SimpleSelectMenu
import com.carpisoft.guau.core.ui.screens.textfields.SimpleTextField
import com.carpisoft.guau.customer.domain.model.IdentificationTypeResp
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.address
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.email
import guau.composeapp.generated.resources.hide
import guau.composeapp.generated.resources.identification_number
import guau.composeapp.generated.resources.identification_type
import guau.composeapp.generated.resources.last_name
import guau.composeapp.generated.resources.name
import guau.composeapp.generated.resources.phone
import guau.composeapp.generated.resources.retry
import guau.composeapp.generated.resources.successful_registration
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddCustomerScreen(
    uiStructureProperties: UiStructureProperties,
    addCustomerViewModel: AddCustomerViewModel,
    onBackOnClick: () -> Unit
) {
    /*BackHandler {
        onBackOnClick()
    }*/
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.AddCustomer)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.onShowActionBottom(true)
        uiStructureProperties.showActionNext(false)
        uiStructureProperties.onShowSaveAction(true)
    }
    val enabledSave by addCustomerViewModel.enabledSave.collectAsState()
    LaunchedEffect(key1 = enabledSave) {
        uiStructureProperties.onEnabledSaveAction(enabledSave)
    }

    val loading by addCustomerViewModel.loading.collectAsState()
    val showError by addCustomerViewModel.showError.collectAsState()
    val identificationTypes by addCustomerViewModel.identificationTypes.collectAsState()
    val identificationTypeSelected by addCustomerViewModel.identificationTypeSelected.collectAsState()
    val identificationNumber by addCustomerViewModel.identificationNumber.collectAsState()
    val name by addCustomerViewModel.name.collectAsState()
    val lastName by addCustomerViewModel.lastName.collectAsState()
    val email by addCustomerViewModel.email.collectAsState()
    val phone by addCustomerViewModel.phone.collectAsState()
    val address by addCustomerViewModel.address.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            SimpleSelectMenu(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = identificationTypeSelected.name,
                label = "${stringResource(Res.string.identification_type)} *",
                placeholder = "",
                list = identificationTypes,
                expanded = expanded,
                arrowOnClick = {
                    expanded = !expanded
                },
                onClickItem = {
                    addCustomerViewModel.onSelectedIdentificationType(it as IdentificationTypeResp)
                    expanded = false
                },
                onDismissRequest = {
                    expanded = false
                },
                getText = {
                    (it as IdentificationTypeResp).name
                }

            )
            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = identificationNumber,
                onValueChange = {
                    addCustomerViewModel.onChangeIdentificationNumber(it)
                },
                label = "${stringResource(Res.string.identification_number)} *",
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = name,
                onValueChange = {
                    addCustomerViewModel.onChangeName(it)
                },
                label = "${stringResource(Res.string.name)} *",
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = lastName,
                onValueChange = {
                    addCustomerViewModel.onChangeLastName(it)
                },
                label = "${stringResource(Res.string.last_name)} *",
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = email,
                onValueChange = {
                    addCustomerViewModel.onChangeEmail(it)
                },
                label = stringResource(Res.string.email),
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = phone,
                onValueChange = {
                    addCustomerViewModel.onChangePhone(it)
                },
                label = stringResource(Res.string.phone),
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            SimpleTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 20.dp),
                value = address,
                onValueChange = {
                    addCustomerViewModel.onChangeAddress(it)
                },
                label = stringResource(Res.string.address),
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
    }
    TwoButtonDialog(
        show = showError,
        message = GetMessageErrorUseCase(errorUi = addCustomerViewModel.getErrorUi() ?: ErrorUi()),
        confirmButtonText = stringResource(Res.string.retry),
        cancelButtonText = stringResource(Res.string.cancel),
        confirmButton = {
            addCustomerViewModel.dismissErrorDialog()
            addCustomerViewModel.loadIdentificationTypeData()
        },
        onDismissRequest = {
            addCustomerViewModel.dismissErrorDialog()
            onBackOnClick()
        })

    val showSuccessDialog by addCustomerViewModel.showSuccessDialog.collectAsState()
    OneButtonDialog(
        show = showSuccessDialog,
        message = stringResource(Res.string.successful_registration),
        onDismissRequest = {
            addCustomerViewModel.dismissSuccessDialog()
            onBackOnClick()
        })

    LaunchedEffect(key1 = 1) {
        addCustomerViewModel.loadIdentificationTypeData()
    }


    val snackBarHostState = remember { SnackbarHostState() }
    DecoupledSnackBar(
        snackBarHostState = snackBarHostState,
        containerColor = Color.Red,
        messageColor = Color.White,
        actionColor = Color.White
    )

    val showErrorSnackBar by addCustomerViewModel.showErrorSnackBar.collectAsState()
    val errorMessage =
        GetMessageErrorUseCase(errorUi = addCustomerViewModel.getErrorUi() ?: ErrorUi())
    val actionLabel = stringResource(Res.string.hide)
    LaunchedEffect(key1 = showErrorSnackBar) {
        if (showErrorSnackBar) {
            snackBarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
            addCustomerViewModel.resetShowErrorSnackBar()
        }
    }
}