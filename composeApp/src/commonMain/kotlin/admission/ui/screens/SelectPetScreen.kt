package admission.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpisoft.guau.SharedRes
import core.ui.constants.ScreenEnum
import core.ui.model.UiStructureProperties
import core.ui.screens.cards.PetCard
import dev.icerock.moko.resources.compose.stringResource
import pet.ui.screens.GetGenderPet
import pet.ui.screens.GetNamePetKind

@Composable
fun SelectPetScreen(
    uiStructureProperties: UiStructureProperties,
    admissionRegisterViewModel: AdmissionRegisterViewModel,
    onSelectAction: () -> Unit
) {
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.SelectPet)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.onShowActionBottom(true)
        uiStructureProperties.showActionNext(true)
        uiStructureProperties.onEnabledNextAction(false)
    }

    val enabledNextAction by admissionRegisterViewModel.enabledNextAction.collectAsState()
    LaunchedEffect(key1 = enabledNextAction) {
        uiStructureProperties.onEnabledNextAction(enabledNextAction)
    }

    val petSelected by admissionRegisterViewModel.petSelected.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
            text = "${stringResource(SharedRes.strings.select_pet)} ${
                stringResource(
                    SharedRes.strings.two_of_four
                )
            }",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        if (petSelected == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .align(Alignment.Center),
                    onClick = onSelectAction
                ) {
                    Icon(imageVector = Icons.Filled.Pets, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = stringResource(SharedRes.strings.select))
                }
            }
        } else {
            Box(Modifier.fillMaxSize()) {
                PetCard(
                    modifier = Modifier.align(Alignment.Center),
                    name = petSelected!!.name,
                    kind = GetNamePetKind(petSelected!!.breed.species.name),
                    breed = petSelected!!.breed.name,
                    gender = GetGenderPet(petSelected!!.gender.name),
                    date = petSelected!!.date,
                    customer = "${petSelected!!.customer.name} ${petSelected!!.customer.lastName}",
                    identificationNumber = petSelected!!.customer.identification,
                    deleteOnClick = {
                        admissionRegisterViewModel.removeSelectedPet()
                        admissionRegisterViewModel.evaluatePetSelected()
                    }
                )
            }
        }
    }


    LaunchedEffect(key1 = 1) {
        admissionRegisterViewModel.evaluatePetSelected()
    }
}