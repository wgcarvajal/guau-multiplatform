package com.carpisoft.guau.admission.ui.screens

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.admission.ui.util.AdmissionHelper
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.cards.PetCard
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimple
import com.carpisoft.guau.pet.domain.model.PetResp
import com.carpisoft.guau.pet.ui.screens.GetGenderPet
import com.carpisoft.guau.pet.ui.screens.GetNamePetKind
import com.carpisoft.guau.pet.ui.screens.PetsScreen
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.next
import guau.composeapp.generated.resources.register_admission
import guau.composeapp.generated.resources.select
import guau.composeapp.generated.resources.select_pet
import guau.composeapp.generated.resources.two_of_four
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class SelectPetScreen : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        val admissionRegisterViewModel = koinViewModel<AdmissionRegisterViewModel>()
        val admissionHelper = koinInject<AdmissionHelper>()
        val petSelected by admissionRegisterViewModel.petSelected.collectAsState()
        Screen(
            petSelected = petSelected,
            onSelectAction = { navigator?.push(item = PetsScreen()) },
            deleteOnClick = {
                admissionHelper.setPetSelected(petResp = null)
                admissionRegisterViewModel.removeSelectedPet()
            },
            onBack = {})
        LaunchedEffect(key1 = 1) {
            if (admissionHelper.getPetSelected() != null) {
                admissionRegisterViewModel.selectedPet(petResp = admissionHelper.getPetSelected()!!)
            }
        }
    }

}

@Composable
fun SelectPetScreen(
    uiStructureProperties: UiStructureProperties,
    onSelectAction: () -> Unit
) {
    val admissionRegisterViewModel = koinViewModel<AdmissionRegisterViewModel>()
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

    val petSelected by admissionRegisterViewModel.petSelected.collectAsState()


}

@Composable
private fun Screen(
    petSelected: PetResp?,
    onSelectAction: () -> Unit,
    deleteOnClick: () -> Unit,
    onBack: () -> Unit
) {
    GuauScaffoldSimple(
        title = stringResource(resource = Res.string.register_admission),
        onBack = onBack
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                text = "${stringResource(Res.string.select_pet)} ${
                    stringResource(
                        Res.string.two_of_four
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
                        Text(text = stringResource(Res.string.select))
                    }
                }
            } else {
                Box(Modifier.fillMaxSize()) {
                    PetCard(
                        modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
                            .padding(top = 30.dp, start = 10.dp, end = 10.dp),
                        name = petSelected!!.name,
                        kind = GetNamePetKind(petSelected!!.breed.species.name),
                        breed = petSelected!!.breed.name,
                        gender = GetGenderPet(petSelected!!.gender.name),
                        date = petSelected!!.date,
                        customer = "${petSelected!!.customer.name} ${petSelected!!.customer.lastName}",
                        identificationNumber = petSelected!!.customer.identification,
                        deleteOnClick = deleteOnClick
                    )
                    Button(
                        onClick = { },
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .padding(end = 20.dp, bottom = 20.dp)
                    ) {
                        Text(text = stringResource(Res.string.next))
                    }
                }
            }
        }
    }
}