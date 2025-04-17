package com.carpisoft.guau.pet.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.itemlist.ItemWithRemove
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimple
import com.carpisoft.guau.navigation.ui.states.BackListener
import com.carpisoft.guau.pet.domain.model.SpeciesResp
import com.carpisoft.guau.pet.ui.util.PetHelper
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.add_pet
import guau.composeapp.generated.resources.are_you_sure_you_want_to_cancel_the_registration_process
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.next
import guau.composeapp.generated.resources.ok
import guau.composeapp.generated.resources.one_of_four
import guau.composeapp.generated.resources.select
import guau.composeapp.generated.resources.select_kind_pet
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class SelectPetTypeScreen : Screen {

    companion object {
        const val TAG = "SelectPetTypeScreen"
    }

    override val key: ScreenKey
        get() = TAG

    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        val addPetViewModel = koinViewModel<AddPetViewModel>()
        val petHelper = koinInject<PetHelper>()
        val typePetSelected by addPetViewModel.typePetSelected.collectAsState()
        var showCancelProcessDialog by rememberSaveable { mutableStateOf(false) }
        Screen(
            typePetSelected = typePetSelected,
            selectOnClick = { navigator?.push(item = SpeciesScreens()) },
            onRemove = {
                petHelper.setTypePetSelected(speciesResp = null)
                addPetViewModel.removeSelectedTypePet()
            }, nextOnclick = {
                navigator?.push(item = SelectBreedScreen())
            }, onBack = {
                showCancelProcessDialog = true
            })
        LaunchedEffect(key1 = 1) {
            if (petHelper.getTypePetSelected() != null) {
                addPetViewModel.selectedTypePet(typePet = petHelper.getTypePetSelected()!!)
            }
        }
        TwoButtonDialog(
            show = showCancelProcessDialog,
            message = stringResource(Res.string.are_you_sure_you_want_to_cancel_the_registration_process),
            confirmButtonText = stringResource(Res.string.ok),
            cancelButtonText = stringResource(Res.string.cancel),
            confirmButton = {
                petHelper.emptyValues()
                showCancelProcessDialog = false
                navigator?.pop()
            },
            onDismissRequest = {
                showCancelProcessDialog = false
            })
        BackListener {
            showCancelProcessDialog = true
        }
    }
}

@Composable
private fun Screen(
    typePetSelected: SpeciesResp,
    selectOnClick: () -> Unit,
    onRemove: () -> Unit,
    nextOnclick: () -> Unit,
    onBack: () -> Unit,
) {
    GuauScaffoldSimple(
        title = stringResource(resource = Res.string.add_pet),
        onBack = onBack
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                text = "${stringResource(Res.string.select_kind_pet)} ${
                    stringResource(
                        Res.string.one_of_four
                    )
                }",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Box(modifier = Modifier.fillMaxSize()) {
                if (typePetSelected.id == "") {
                    Button(
                        modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                            .padding(start = 10.dp, end = 10.dp),
                        onClick = selectOnClick
                    ) {
                        Text(
                            text = stringResource(Res.string.select)
                        )
                    }

                } else {
                    ItemWithRemove(
                        modifier = Modifier.align(Alignment.TopCenter)
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                        text = GetNamePetKind(name = typePetSelected.name),
                        imageUrl = typePetSelected.image,
                        onRemove = onRemove
                    )
                    Button(
                        onClick = nextOnclick,
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

@Composable
fun SelectPetTypeScreen(
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
        uiStructureProperties.onSetTitle(ScreenEnum.SelectPetType)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.onShowActionBottom(true)
        uiStructureProperties.showActionNext(true)
        uiStructureProperties.onShowSaveAction(false)

    }

    val typePetSelected by addPetViewModel.typePetSelected.collectAsState()

    LaunchedEffect(key1 = 1) {

    }
}

