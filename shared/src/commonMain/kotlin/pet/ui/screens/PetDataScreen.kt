package pet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpisoft.guau.SharedRes
import core.domain.usecase.GetMessageErrorUseCase
import core.ui.constants.ScreenEnum
import core.ui.model.ErrorUi
import core.ui.model.UiStructureProperties
import core.ui.screens.datepicker.SimpleDatePickerDialog
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.loading.SimpleLoading
import core.ui.screens.textfields.SimpleSelectMenu
import core.ui.screens.textfields.SimpleTextField
import core.utils.date.DateTime
import dev.icerock.moko.resources.compose.stringResource
import pet.domain.model.GenderResp
import ui.theme.Background
import ui.theme.OnSurface
import ui.theme.OnSurfaceVariant
import ui.theme.Outline

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDataScreen(
    uiStructureProperties: UiStructureProperties,
    addPetViewModel: AddPetViewModel,
    selectOnClick: () -> Unit,
    onBackOnClick: () -> Unit
) {

    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.PetData)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.onShowActionBottom(true)
        uiStructureProperties.showActionNext(true)
        uiStructureProperties.onShowSaveAction(false)
    }
    val enabledNextAction by addPetViewModel.enabledNextAction.collectAsState()
    val customerSelected by addPetViewModel.customerSelected.collectAsState()
    val birthdate by addPetViewModel.birthdate.collectAsState()
    val genderSelected by addPetViewModel.genderSelected.collectAsState()
    val genders by addPetViewModel.genders.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val loading by addPetViewModel.loading.collectAsState()
    val showError by addPetViewModel.showError.collectAsState()
    val name by addPetViewModel.name.collectAsState()
    val description by addPetViewModel.description.collectAsState()
    LaunchedEffect(key1 = enabledNextAction) {
        uiStructureProperties.onEnabledNextAction(enabledNextAction)
    }
    var showCalendar by rememberSaveable { mutableStateOf(false) }
    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                text = "${stringResource(SharedRes.strings.pet_data)} ${
                    stringResource(
                        SharedRes.strings.three_of_four
                    )
                }",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            SimpleSelectMenu(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = if (genderSelected.id == -1) {
                    ""
                } else {
                    GetGenderPet(genderSelected.name)
                },
                label = "${stringResource(SharedRes.strings.gender)} *",
                placeholder = "",
                list = genders,
                expanded = expanded,
                arrowOnClick = {
                    expanded = !expanded
                },
                onClickItem = {
                    addPetViewModel.onSelectedGender(it as GenderResp)
                    expanded = false
                },
                onDismissRequest = {
                    expanded = false
                },
                getText = {
                    GetGenderPet((it as GenderResp).name)
                }
            )

            SimpleTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = if (birthdate.isNotEmpty()) {
                    DateTime.getFormattedDate(birthdate.toLong(), "dd/MM/yyyy")
                } else {
                    ""
                },
                onValueChange = {

                },
                label = "${stringResource(SharedRes.strings.birthdate)} *",
                placeholder = "",
                trailingIcon = {
                    IconButton(onClick = { showCalendar = true }) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = ""
                        )
                    }
                },
                enabled = false,
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Outline,
                    disabledLabelColor = OnSurfaceVariant,
                    disabledTrailingIconColor = OnSurfaceVariant,
                    disabledPlaceholderColor = OnSurfaceVariant,
                    disabledTextColor = OnSurface
                )
            )

            SimpleTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = name,
                onValueChange = {
                    addPetViewModel.onChangeName(it)
                },
                label = "${stringResource(SharedRes.strings.name)} *",
                placeholder = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            SimpleTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = description,
                onValueChange = {
                    addPetViewModel.onChangeDescription(it)
                },
                label = stringResource(SharedRes.strings.description),
                placeholder = "",
                maxLines = 5,
                minLines = 3,
                singleLine = false,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(
                            width = 1.dp,
                            color = OnSurfaceVariant,
                            shape = RoundedCornerShape(5)
                        )
                        .clip(shape = RoundedCornerShape(5))
                )
                {
                    if (customerSelected.id != -1L) {
                        Column(modifier = Modifier.weight(4f)) {
                            Text(
                                modifier = Modifier.padding(top = 20.dp, start = 10.dp),
                                text = "${customerSelected.identificationType.name}: ${customerSelected.identification}",
                                fontSize = 12.sp
                            )
                            Text(
                                modifier = Modifier.padding(
                                    top = 10.dp,
                                    start = 10.dp,
                                    bottom = 20.dp
                                ),
                                text = "${customerSelected.name} ${customerSelected.lastName}",
                                fontSize = 12.sp
                            )
                        }
                        Box(
                            modifier = Modifier.weight(1f)
                                .align(Alignment.CenterVertically)
                        ) {
                            IconButton(
                                modifier = Modifier.align(Alignment.Center),
                                onClick = { addPetViewModel.resetCustomerSelected() }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "remove owner"
                                )
                            }
                        }
                    } else {

                        Box(
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp)
                        ) {
                            Button(
                                onClick = selectOnClick,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            {
                                Text(text = stringResource(SharedRes.strings.associate))
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier.height(20.dp).padding(start = 10.dp).background(Background)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                        text = "${stringResource(SharedRes.strings.owner)} *",
                        color = OnSurfaceVariant
                    )
                }
            }

        }
    }
    TwoButtonDialog(
        show = showError,
        message = GetMessageErrorUseCase(errorUi = addPetViewModel.getErrorUi() ?: ErrorUi()),
        confirmButtonText = stringResource(SharedRes.strings.retry),
        cancelButtonText = stringResource(SharedRes.strings.cancel),
        confirmButton = {
            addPetViewModel.dismissErrorDialog()
            addPetViewModel.loadBreedData()
        },
        onDismissRequest = {
            addPetViewModel.dismissErrorDialog()
            onBackOnClick()
        })
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = DateTime.getCurrentDate())
    if (showCalendar) {
        SimpleDatePickerDialog(
            datePickerState = datePickerState,
            onDateSelected = {
                addPetViewModel.changeBirthdate(it)
            },
            onDismiss = {
                showCalendar = false
            },
            dateValidator = {
                it <= DateTime.getCurrentDate()
            },
        )
    }
    LaunchedEffect(key1 = 1) {
        addPetViewModel.validateEnabledNext()
        addPetViewModel.loadGendersData()
    }
}