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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.itemlist.ItemWithRemove
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.one_of_four
import guau.composeapp.generated.resources.select
import guau.composeapp.generated.resources.select_kind_pet
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SelectPetTypeScreen(
    uiStructureProperties: UiStructureProperties,
    addPetViewModel: AddPetViewModel,
    selectOnClick: () -> Unit,
    onBackOnClick: () -> Unit
) {

    /*BackHandler {
        onBackOnClick()
    }*/
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
    val enabledNextAction by addPetViewModel.enabledNextAction.collectAsState()
    LaunchedEffect(key1 = enabledNextAction) {
        uiStructureProperties.onEnabledNextAction(enabledNextAction)
    }
    val typePetSelected by addPetViewModel.typePetSelected.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
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
            if (typePetSelected.id == -1) {

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
                    modifier = Modifier.align(Alignment.Center).padding(start = 20.dp, end = 20.dp),
                    text = GetNamePetKind(name = typePetSelected.name),
                    imageUrl = typePetSelected.image,
                    onRemove = {
                        addPetViewModel.removeSelectedTypePet()
                        addPetViewModel.evaluateSpeciesSelected()
                    }
                )
            }
        }
    }
    LaunchedEffect(key1 = 1) {
        addPetViewModel.evaluateSpeciesSelected()
    }
}

