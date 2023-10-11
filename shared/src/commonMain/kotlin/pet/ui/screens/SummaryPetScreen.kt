package pet.ui.screens

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
import com.carpisoft.guau.SharedRes
import core.domain.usecase.GetMessageErrorUseCase
import core.ui.constants.ScreenEnum
import core.ui.model.ErrorUi
import core.ui.model.UiStructureProperties
import core.ui.screens.dialogs.OneButtonDialog
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.loading.SimpleLoading
import core.utils.date.DateTime
import core.utils.date.DateTime.ageCalculateMonths
import core.utils.date.DateTime.ageCalculateYears
import dev.icerock.moko.resources.compose.stringResource

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

    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 10.dp, start = 10.dp, end = 15.dp, bottom = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${stringResource(SharedRes.strings.summary)} ${
                    stringResource(
                        SharedRes.strings.four_of_four
                    )
                }",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 5.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "${stringResource(SharedRes.strings.pet_kind)}:",
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
                    text = "${stringResource(SharedRes.strings.breed)}:",
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
                    text = "${stringResource(SharedRes.strings.gender)}:",
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
                    text = "${stringResource(SharedRes.strings.birthdate)}:",
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
                    text = "${stringResource(SharedRes.strings.age)}:",
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
                            "$year ${stringResource(SharedRes.strings.year_string)}"
                        } else {
                            "$year ${stringResource(SharedRes.strings.years_string)}"
                        }
                    } else {
                        val month = if (birthdate.isNotEmpty()) {
                            ageCalculateMonths(birthdate.toLong())
                        } else {
                            0
                        }
                        if (month == 1) {
                            "$month ${stringResource(SharedRes.strings.month_string)}"
                        } else {
                            "$month ${stringResource(SharedRes.strings.months_string)}"
                        }
                    }, textAlign = TextAlign.End
                )
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "${stringResource(SharedRes.strings.name)}:",
                    fontWeight = FontWeight.Medium
                )
                Text(modifier = Modifier.fillMaxWidth(), text = name, textAlign = TextAlign.End)
            }
            if (description.isNotEmpty()) {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp)) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "${stringResource(SharedRes.strings.description)}:",
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
                    text = "${stringResource(SharedRes.strings.identification_type)}:",
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
                    text = "${stringResource(SharedRes.strings.identification_number)}:",
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
                    text = "${stringResource(SharedRes.strings.name)}:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${customerSelected.name} ${customerSelected.lastName}",
                    textAlign = TextAlign.End
                )
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
        },
        onDismissRequest = {
            addPetViewModel.dismissErrorDialog()
        })
    val showSuccessDialog by addPetViewModel.showSuccessDialog.collectAsState()
    OneButtonDialog(
        show = showSuccessDialog,
        message = stringResource(SharedRes.strings.successful_registration),
        onDismissRequest = {
            addPetViewModel.dismissSuccessDialog()
            onSaveSuccess()
            addPetViewModel.emptyValues()
        })
}