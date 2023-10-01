package customer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.SharedRes
import core.domain.usecase.GetMessageErrorUseCase
import core.ui.constants.ScreenEnum
import core.ui.model.ErrorUi
import core.ui.model.UiStructureProperties
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.loading.SimpleLoading
import core.ui.screens.textfields.SimpleSelectMenu
import core.ui.screens.textfields.SimpleTextField
import customer.domain.model.IdentificationTypeResp
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AddCustomerScreen(
    uiStructureProperties: UiStructureProperties,
    addCustomerViewModel: AddCustomerViewModel,
    onBackOnClick: () -> Unit
) {
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
    LaunchedEffect(key1 = enabledSave){
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
                label = "${stringResource(SharedRes.strings.identification_type)} *",
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
                label = "${stringResource(SharedRes.strings.identification_number)} *",
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = name,
                onValueChange = {
                    addCustomerViewModel.onChangeName(it)
                },
                label = "${stringResource(SharedRes.strings.name)} *",
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = lastName,
                onValueChange = {
                    addCustomerViewModel.onChangeLastName(it)
                },
                label = "${stringResource(SharedRes.strings.last_name)} *",
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = email,
                onValueChange = {
                    addCustomerViewModel.onChangeEmail(it)
                },
                label = stringResource(SharedRes.strings.email),
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = phone,
                onValueChange = {
                    addCustomerViewModel.onChangePhone(it)
                },
                label = stringResource(SharedRes.strings.phone),
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
                label = stringResource(SharedRes.strings.address),
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
    }
    TwoButtonDialog(
        show = showError,
        message = GetMessageErrorUseCase(errorUi = addCustomerViewModel.getErrorUi() ?: ErrorUi()),
        confirmButtonText = stringResource(SharedRes.strings.retry),
        cancelButtonText = stringResource(SharedRes.strings.cancel),
        confirmButton = {
            addCustomerViewModel.dismissErrorDialog()
            addCustomerViewModel.loadIdentificationTypeData()
        },
        onDismissRequest = {
            addCustomerViewModel.dismissErrorDialog()
            onBackOnClick()
        })

    LaunchedEffect(key1 = 1) {
        addCustomerViewModel.loadIdentificationTypeData()
    }
}