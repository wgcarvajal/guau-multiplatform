package com.carpisoft.guau.pet.ui.screens

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
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.screens.buttons.NextButtonIntoBox
import com.carpisoft.guau.core.ui.screens.datepicker.SimpleDatePickerDialog
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimple
import com.carpisoft.guau.core.ui.screens.textfields.SimpleSelectMenu
import com.carpisoft.guau.core.ui.screens.textfields.SimpleTextField
import com.carpisoft.guau.core.utils.date.DateTime
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.customer.ui.CustomersScreen
import com.carpisoft.guau.pet.domain.model.GenderResp
import com.carpisoft.guau.pet.ui.util.PetHelper
import com.carpisoft.guau.ui.theme.Background
import com.carpisoft.guau.ui.theme.OnSurface
import com.carpisoft.guau.ui.theme.OnSurfaceVariant
import com.carpisoft.guau.ui.theme.Outline
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.add_pet
import guau.composeapp.generated.resources.associate
import guau.composeapp.generated.resources.birthdate
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.description
import guau.composeapp.generated.resources.gender
import guau.composeapp.generated.resources.name
import guau.composeapp.generated.resources.owner
import guau.composeapp.generated.resources.pet_data
import guau.composeapp.generated.resources.retry
import guau.composeapp.generated.resources.three_of_four
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class PetDataScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        val addPetViewModel = koinViewModel<AddPetViewModel>()
        val petHelper = koinInject<PetHelper>()
        val customerSelected by addPetViewModel.customerSelected.collectAsState()
        val birthdate by addPetViewModel.birthdate.collectAsState()
        val genderSelected by addPetViewModel.genderSelected.collectAsState()
        val genders by addPetViewModel.genders.collectAsState()
        val loading by addPetViewModel.loading.collectAsState()
        val showError by addPetViewModel.showError.collectAsState()
        val name by addPetViewModel.name.collectAsState()
        val description by addPetViewModel.description.collectAsState()
        val enabledNextAction by addPetViewModel.enabledNextAction.collectAsState()
        var showCalendar by rememberSaveable { mutableStateOf(false) }
        Screen(
            genders = genders,
            loading = loading,
            name = name,
            description = description,
            showError = showError,
            birthdate = birthdate,
            customerSelected = customerSelected,
            genderSelected = genderSelected,
            enabledNextAction = enabledNextAction,
            onClickItem = {
                addPetViewModel.onSelectedGender(it)
                petHelper.setGenderSelected(genderSelected = it)
            },
            onValueChangeName = {
                petHelper.setName(addPetViewModel.onChangeName(it))
            }, onValueChangeDescription = {
                petHelper.setDescription(addPetViewModel.onChangeDescription(it))
            }, resetCustomerOnClick = {
                petHelper.setCustomerSelected(customerResp = null)
                addPetViewModel.resetCustomerSelected()
            }, associateOnClick = {
                navigator?.push(item = CustomersScreen())
            }, calendarIconOnClick = {
                showCalendar = true
            }, nextOnclick = {
                navigator?.push(item = SummaryPetScreen())
            }, onback = {
                navigator?.pop()
            })
        TwoButtonDialog(
            show = showError,
            message = GetMessageErrorUseCase(errorUi = addPetViewModel.getErrorUi() ?: ErrorUi()),
            confirmButtonText = stringResource(Res.string.retry),
            cancelButtonText = stringResource(Res.string.cancel),
            confirmButton = {
                addPetViewModel.dismissErrorDialog()
                addPetViewModel.loadBreedData()
            },
            onDismissRequest = {
                addPetViewModel.dismissErrorDialog()
                //onBackOnClick()
            })
        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis = DateTime.getCurrentDate(),
                selectableDates = object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        return utcTimeMillis <= DateTime.getCurrentDate()
                    }

                })
        if (showCalendar) {
            SimpleDatePickerDialog(
                datePickerState = datePickerState,
                onDateSelected = {
                    addPetViewModel.changeBirthdate(it)
                    petHelper.setBirthdate(birthdate = it)
                },
                onDismiss = {
                    showCalendar = false
                }
            )
        }
        LaunchedEffect(key1 = 1) {
            addPetViewModel.selectedTypePet(typePet = petHelper.getTypePetSelected()!!)
            addPetViewModel.selectedBreed(breed = petHelper.getBreedPetSelected()!!)
            if (petHelper.getName().isNotEmpty()) {
                addPetViewModel.setName(name = petHelper.getName())
            }
            if (petHelper.getDescription().isNotEmpty()) {
                addPetViewModel.setDescription(description = petHelper.getDescription())
            }
            if (petHelper.getBirthdate().isNotEmpty()) {
                addPetViewModel.changeBirthdate(value = petHelper.getBirthdate())
            }
            if (petHelper.getGenderSelected() != null) {
                addPetViewModel.onSelectedGender(petHelper.getGenderSelected()!!)
            }
            if (petHelper.getCustomerSelected() != null) {
                addPetViewModel.selectedCustomer(petHelper.getCustomerSelected()!!)
            }
            addPetViewModel.validateEnabledNext()
            addPetViewModel.loadGendersData()
        }
    }
}

@Composable
private fun Screen(
    genders: List<GenderResp>,
    genderSelected: GenderResp,
    name: String,
    description: String,
    birthdate: String,
    customerSelected: CustomerResp,
    loading: Boolean,
    showError: Boolean,
    enabledNextAction: Boolean,
    calendarIconOnClick: () -> Unit,
    onClickItem: (GenderResp) -> Unit,
    onValueChangeName: (String) -> Unit,
    onValueChangeDescription: (String) -> Unit,
    resetCustomerOnClick: () -> Unit,
    associateOnClick: () -> Unit,
    nextOnclick: () -> Unit,
    onback: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    GuauScaffoldSimple(
        title = stringResource(resource = Res.string.add_pet),
        showExitCenter = false,
        showAddActionButton = false,
        showAccountOptions = false,
        onBack = onback
    ) { paddingValues ->
        if (loading) {
            SimpleLoading()
        } else if (!showError) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
                Column(modifier = Modifier.fillMaxSize().padding(bottom = 60.dp)) {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                        text = "${stringResource(Res.string.pet_data)} ${
                            stringResource(
                                Res.string.three_of_four
                            )
                        }",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {


                        SimpleSelectMenu(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            value = if (genderSelected.id == -1) {
                                ""
                            } else {
                                GetGenderPet(genderSelected.name)
                            },
                            label = "${stringResource(Res.string.gender)} *",
                            placeholder = "",
                            list = genders,
                            expanded = expanded,
                            arrowOnClick = {
                                expanded = !expanded
                            },
                            onClickItem = {
                                onClickItem(it as GenderResp)
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
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            value = if (birthdate.isNotEmpty()) {
                                DateTime.getFormattedDate(birthdate.toLong(), "dd/MM/yyyy")
                            } else {
                                ""
                            },
                            onValueChange = {

                            },
                            label = "${stringResource(Res.string.birthdate)} *",
                            placeholder = "",
                            trailingIcon = {
                                IconButton(onClick = calendarIconOnClick) {
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
                            onValueChange = onValueChangeName,
                            label = "${stringResource(Res.string.name)} *",
                            placeholder = "",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        SimpleTextField(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            value = description,
                            onValueChange = onValueChangeDescription,
                            label = stringResource(Res.string.description),
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
                                if (customerSelected.id != "") {
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
                                            onClick = resetCustomerOnClick
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "remove owner"
                                            )
                                        }
                                    }
                                } else {

                                    Box(
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(top = 10.dp, bottom = 10.dp)
                                    ) {
                                        Button(
                                            onClick = associateOnClick,
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                        {
                                            Text(text = stringResource(Res.string.associate))
                                        }
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier.height(20.dp).padding(start = 10.dp)
                                    .background(Background)
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                                    text = "${stringResource(Res.string.owner)} *",
                                    color = OnSurfaceVariant
                                )
                            }
                        }

                    }
                }
                NextButtonIntoBox(nextOnclick = nextOnclick, enabled = enabledNextAction)
            }
        }
    }
}