package pet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.carpisoft.guau.SharedRes
import core.domain.usecase.GetMessageErrorUseCase
import core.ui.constants.ScreenEnum
import core.ui.model.ErrorUi
import core.ui.model.UiStructureProperties
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.itemlist.ItemPetType
import core.ui.screens.loading.SimpleLoading
import dev.icerock.moko.resources.compose.stringResource

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
    }
    val loading by addPetViewModel.loading.collectAsState()
    val typePets by addPetViewModel.typePets.collectAsState()
    val showError by addPetViewModel.showError.collectAsState()
    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        Column(modifier = Modifier.fillMaxSize()) {
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
                                addPetViewModel.selectedTypePet(typePet = typePet)
                                onBackOnClick()
                            },
                        )
                    }
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
            addPetViewModel.loadSpeciesData()
        },
        onDismissRequest = {
            addPetViewModel.dismissErrorDialog()
            onBackOnClick()
        })
    LaunchedEffect(key1 = 1) {
        addPetViewModel.loadSpeciesData()
    }
}