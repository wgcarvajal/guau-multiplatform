package pet.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpisoft.guau.SharedRes
import core.ui.constants.ScreenEnum
import core.ui.model.UiStructureProperties
import core.ui.screens.itemlist.ItemWithRemove
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SelectBreedScreen(
    uiStructureProperties: UiStructureProperties,
    addPetViewModel: AddPetViewModel,
    selectOnClick: () -> Unit
) {
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.SelectBreed)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.onShowActionBottom(true)
        uiStructureProperties.showActionNext(true)
        uiStructureProperties.onShowSaveAction(false)
    }
    val enabledNextAction by addPetViewModel.enabledNextAction.collectAsState()
    LaunchedEffect(key1 = enabledNextAction) {
        uiStructureProperties.onEnabledNextAction(enabledNextAction)
    }

    val breedSelected by addPetViewModel.breedSelected.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
            text = "${stringResource(SharedRes.strings.select_breed)} ${
                stringResource(
                    SharedRes.strings.two_of_four
                )
            }",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (breedSelected.id == -1) {
                Button(
                    modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                        .padding(start = 10.dp, end = 10.dp),
                    onClick = selectOnClick
                ) {
                    Text(
                        text = stringResource(SharedRes.strings.select)
                    )
                }
            } else {
                ItemWithRemove(
                    modifier = Modifier.align(Alignment.Center).padding(start = 20.dp, end = 20.dp),
                    text = breedSelected.name,
                    imageUrl = breedSelected.image,
                    onRemove = {
                        addPetViewModel.removeSelectedBreed()
                        addPetViewModel.evaluateBreedSelected()
                    }
                )
            }
        }
    }
    LaunchedEffect(key1 = 1) {
        addPetViewModel.evaluateBreedSelected()
    }

}