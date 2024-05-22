package com.carpisoft.guau.admission.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.itemlist.ItemAdmissionType
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.consultations
import guau.composeapp.generated.resources.emergencies
import guau.composeapp.generated.resources.one_of_four
import guau.composeapp.generated.resources.select_the_type_of_admission
import guau.composeapp.generated.resources.vaccines
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import com.carpisoft.guau.ui.theme.Orange
import com.carpisoft.guau.ui.theme.Purple

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AdmissionTypeScreen(
    uiStructureProperties: UiStructureProperties,
    selectAdmissionType: () -> Unit
) {
    val admissionRegisterViewModel = GetAdmissionRegisterViewModel()
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(false)
        uiStructureProperties.onSetTitle(ScreenEnum.AdmissionType)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.showActionNext(false)
        uiStructureProperties.onEnabledNextAction(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
            text = "${stringResource(Res.string.select_the_type_of_admission)} ${
                stringResource(
                    Res.string.one_of_four
                )
            }",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize()
                .padding(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
            columns = GridCells.Fixed(count = 2),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                ItemAdmissionType(
                    text = stringResource(Res.string.emergencies),
                    backgroundColor = Color.Red,
                    onClick = {
                        admissionRegisterViewModel.admissionType = AdmissionTypeEnum.Emergencies
                        selectAdmissionType()
                    }
                )
            }
            item {
                ItemAdmissionType(
                    text = stringResource(Res.string.consultations),
                    backgroundColor = Orange,
                    onClick = {
                        admissionRegisterViewModel.admissionType = AdmissionTypeEnum.Consultations
                        selectAdmissionType()
                    }
                )
            }
            item {
                ItemAdmissionType(
                    text = stringResource(Res.string.vaccines),
                    backgroundColor = Purple,
                    onClick = {
                        admissionRegisterViewModel.admissionType = AdmissionTypeEnum.Consultations
                        selectAdmissionType()
                    }
                )
            }
        }
    }
}