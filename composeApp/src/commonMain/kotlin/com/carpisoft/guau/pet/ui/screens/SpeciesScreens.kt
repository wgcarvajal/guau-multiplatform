package com.carpisoft.guau.pet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.itemlist.ItemPetType
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimple
import com.carpisoft.guau.pet.domain.model.SpeciesResp
import com.carpisoft.guau.pet.ui.util.PetHelper
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.retry
import guau.composeapp.generated.resources.select_kind_pet
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class SpeciesScreens : Screen {
    @Composable
    override fun Content() {
        val addPetViewModel = koinViewModel<AddPetViewModel>()
        val petHelper = koinInject<PetHelper>()
        val navigator: Navigator? = LocalNavigator.current
        val loading by addPetViewModel.loading.collectAsState()
        val typePets by addPetViewModel.typePets.collectAsState()
        val showError by addPetViewModel.showError.collectAsState()
        Screen(loading = loading, typePets = typePets, showError = showError, selectTypePet = {
            petHelper.setTypePetSelected(speciesResp = it)
            navigator?.pop()
        })
        TwoButtonDialog(
            show = showError,
            message = GetMessageErrorUseCase(errorUi = addPetViewModel.getErrorUi() ?: ErrorUi()),
            confirmButtonText = stringResource(Res.string.retry),
            cancelButtonText = stringResource(Res.string.cancel),
            confirmButton = {
                addPetViewModel.dismissErrorDialog()
                addPetViewModel.loadSpeciesData()
            },
            onDismissRequest = {
                addPetViewModel.dismissErrorDialog()
                //onBackOnClick()
            })
        LaunchedEffect(key1 = 1) {
            addPetViewModel.loadSpeciesData()
        }
    }

}

@Composable
private fun Screen(
    typePets: List<SpeciesResp>,
    loading: Boolean,
    showError: Boolean,
    selectTypePet: (SpeciesResp) -> Unit
) {
    GuauScaffoldSimple(
        title = stringResource(resource = Res.string.select_kind_pet),
        onBack = {}
    ) { paddingValues ->
        if (loading) {
            SimpleLoading()
        } else if (!showError) {
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                        columns = GridCells.Fixed(count = 2),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(typePets) { typePet ->
                            ItemPetType(
                                text = GetNamePetKind(typePet.name),
                                imageUrl = typePet.image,
                                onClick = {
                                    selectTypePet(typePet)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpeciesScreens(
    uiStructureProperties: UiStructureProperties,
    addPetViewModel: AddPetViewModel,
    onBackOnClick: () -> Unit
) {

    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.PetTypes)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.showActionNext(false)
        uiStructureProperties.onEnabledNextAction(false)
        uiStructureProperties.onShowActionBottom(false)
        uiStructureProperties.onShowSaveAction(false)
        uiStructureProperties.onEnabledSaveAction(false)
    }
    val loading by addPetViewModel.loading.collectAsState()
    val typePets by addPetViewModel.typePets.collectAsState()
    val showError by addPetViewModel.showError.collectAsState()

    LaunchedEffect(key1 = 1) {
        addPetViewModel.loadSpeciesData()
    }
}