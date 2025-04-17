package com.carpisoft.guau.pet.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.buttons.SaveButtonIntoBox
import com.carpisoft.guau.core.ui.screens.dialogs.OneButtonDialog
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimple
import com.carpisoft.guau.core.utils.date.DateTime
import com.carpisoft.guau.core.utils.date.DateTime.ageCalculateMonths
import com.carpisoft.guau.core.utils.date.DateTime.ageCalculateYears
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.pet.domain.model.BreedResp
import com.carpisoft.guau.pet.domain.model.GenderResp
import com.carpisoft.guau.pet.domain.model.SpeciesResp
import com.carpisoft.guau.pet.ui.util.PetHelper
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.age
import guau.composeapp.generated.resources.birthdate
import guau.composeapp.generated.resources.breed
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.description
import guau.composeapp.generated.resources.four_of_four
import guau.composeapp.generated.resources.gender
import guau.composeapp.generated.resources.identification_number
import guau.composeapp.generated.resources.identification_type
import guau.composeapp.generated.resources.month_string
import guau.composeapp.generated.resources.months_string
import guau.composeapp.generated.resources.name
import guau.composeapp.generated.resources.pet_kind
import guau.composeapp.generated.resources.retry
import guau.composeapp.generated.resources.successful_registration
import guau.composeapp.generated.resources.summary
import guau.composeapp.generated.resources.year_string
import guau.composeapp.generated.resources.years_string
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class SummaryPetScreen : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        val addPetViewModel = koinViewModel<AddPetViewModel>()
        val petHelper = koinInject<PetHelper>()
        val loading by addPetViewModel.loading.collectAsState()
        val showError by addPetViewModel.showError.collectAsState()
        val typePetSelected by addPetViewModel.typePetSelected.collectAsState()
        val breedSelected by addPetViewModel.breedSelected.collectAsState()
        val name by addPetViewModel.name.collectAsState()
        val genderSelected by addPetViewModel.genderSelected.collectAsState()
        val customerSelected by addPetViewModel.customerSelected.collectAsState()
        val description by addPetViewModel.description.collectAsState()
        val birthdate by addPetViewModel.birthdate.collectAsState()
        Screen(
            loading = loading,
            showError = showError,
            typePetSelected = typePetSelected,
            breedSelected = breedSelected,
            genderSelected = genderSelected,
            customerSelected = customerSelected,
            description = description,
            name = name,
            birthdate = birthdate,
            onBack = {
                navigator?.pop()
            },
            saveOnClick = {
                addPetViewModel.save()
            }
        )
        TwoButtonDialog(
            show = showError,
            message = GetMessageErrorUseCase(errorUi = addPetViewModel.getErrorUi() ?: ErrorUi()),
            confirmButtonText = stringResource(Res.string.retry),
            cancelButtonText = stringResource(Res.string.cancel),
            confirmButton = {
                addPetViewModel.dismissErrorDialog()
            },
            onDismissRequest = {
                addPetViewModel.dismissErrorDialog()
            })
        val showSuccessDialog by addPetViewModel.showSuccessDialog.collectAsState()
        OneButtonDialog(
            show = showSuccessDialog,
            message = stringResource(Res.string.successful_registration),
            onDismissRequest = {
                addPetViewModel.dismissSuccessDialog()
                petHelper.emptyValues()
                navigator?.popUntil(predicate = {
                    it.key == PetsScreen.TAG
                })
            })
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
        }
    }


}

@Composable
private fun Screen(
    loading: Boolean,
    showError: Boolean,
    typePetSelected: SpeciesResp,
    breedSelected: BreedResp,
    genderSelected: GenderResp,
    customerSelected: CustomerResp,
    description: String,
    name: String,
    birthdate: String,
    onBack: () -> Unit,
    saveOnClick: () -> Unit
) {
    GuauScaffoldSimple(
        title = stringResource(resource = Res.string.summary),
        showExitCenter = false,
        showAddActionButton = false,
        showAccountOptions = false,
        onBack = onBack
    ) { paddingValues ->
        if (loading) {
            SimpleLoading()
        } else if (!showError) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = 10.dp, start = 10.dp, end = 15.dp, bottom = 10.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${stringResource(Res.string.summary)} ${
                            stringResource(
                                Res.string.four_of_four
                            )
                        }",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 5.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${stringResource(Res.string.pet_kind)}:",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = GetNamePetKind(typePetSelected.name),
                            textAlign = TextAlign.End
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${stringResource(Res.string.breed)}:",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = breedSelected.name,
                            textAlign = TextAlign.End
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${stringResource(Res.string.gender)}:",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = GetGenderPet(genderSelected.name),
                            textAlign = TextAlign.End
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${stringResource(Res.string.birthdate)}:",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = if (birthdate.isNotEmpty()) {
                                DateTime.getFormattedDate(birthdate.toLong(), "dd MMM yyyy")
                            } else {
                                ""
                            },
                            textAlign = TextAlign.End
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${stringResource(Res.string.age)}:",
                            fontWeight = FontWeight.Medium
                        )
                        val year = if (birthdate.isNotEmpty()) {
                            ageCalculateYears(birthdate.toLong())
                        } else {
                            0
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(), text = if (year > 0) {
                                if (year == 1) {
                                    "$year ${stringResource(Res.string.year_string)}"
                                } else {
                                    "$year ${stringResource(Res.string.years_string)}"
                                }
                            } else {
                                val month = if (birthdate.isNotEmpty()) {
                                    ageCalculateMonths(birthdate.toLong())
                                } else {
                                    0
                                }
                                if (month == 1) {
                                    "$month ${stringResource(Res.string.month_string)}"
                                } else {
                                    "$month ${stringResource(Res.string.months_string)}"
                                }
                            }, textAlign = TextAlign.End
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${stringResource(Res.string.name)}:",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = name,
                            textAlign = TextAlign.End
                        )
                    }
                    if (description.isNotEmpty()) {
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = "${stringResource(Res.string.description)}:",
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = description,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${stringResource(Res.string.identification_type)}:",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = customerSelected.identificationType.name,
                            textAlign = TextAlign.End
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${stringResource(Res.string.identification_number)}:",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = customerSelected.identification,
                            textAlign = TextAlign.End
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${stringResource(Res.string.name)}:",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "${customerSelected.name} ${customerSelected.lastName}",
                            textAlign = TextAlign.End
                        )
                    }
                }
                SaveButtonIntoBox(enabled = true, saveOnclick = saveOnClick)
            }
        }
    }
}

@Composable
fun SummaryPetScreen(
    uiStructureProperties: UiStructureProperties,
    addPetViewModel: AddPetViewModel,
    onSaveSuccess: () -> Unit
) {
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.SummaryPet)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.onShowActionBottom(true)
        uiStructureProperties.showActionNext(false)
        uiStructureProperties.onShowSaveAction(true)
        uiStructureProperties.onEnabledSaveAction(true)
    }

    val loading by addPetViewModel.loading.collectAsState()
    val showError by addPetViewModel.showError.collectAsState()
    val typePetSelected by addPetViewModel.typePetSelected.collectAsState()
    val breedSelected by addPetViewModel.breedSelected.collectAsState()
    val name by addPetViewModel.name.collectAsState()
    val genderSelected by addPetViewModel.genderSelected.collectAsState()
    val customerSelected by addPetViewModel.customerSelected.collectAsState()
    val description by addPetViewModel.description.collectAsState()
    val birthdate by addPetViewModel.birthdate.collectAsState()


}