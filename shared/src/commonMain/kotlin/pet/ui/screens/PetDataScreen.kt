package pet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpisoft.guau.SharedRes
import core.ui.constants.ScreenEnum
import core.ui.model.UiStructureProperties
import core.ui.screens.textfields.SimpleTextField
import dev.icerock.moko.resources.compose.stringResource
import ui.theme.Background
import ui.theme.OnSurface
import ui.theme.OnSurfaceVariant
import ui.theme.Outline

@Composable
fun PetDataScreen(
    uiStructureProperties: UiStructureProperties,
    addPetViewModel: AddPetViewModel,
    selectOnClick: () -> Unit
) {

    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.PetData)
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

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
            text = "${stringResource(SharedRes.strings.pet_data)} ${
                stringResource(
                    SharedRes.strings.three_of_five
                )
            }",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        SimpleTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
            value = "",
            onValueChange = {

            },
            label = "${stringResource(SharedRes.strings.gender)} *",
            placeholder = "",
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = ""
                    )
                }
            },
            enabled = false,
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Outline,
                disabledLabelColor = OnSurfaceVariant,
                disabledTrailingIconColor = OnSurfaceVariant,
                disabledPlaceholderColor = OnSurfaceVariant,
                disabledTextColor = OnSurface
            )

        )

        SimpleTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
            value = "",
            onValueChange = {

            },
            label = "${stringResource(SharedRes.strings.birthdate)} *",
            placeholder = "",
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = ""
                    )
                }
            },
            enabled = false,
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Outline,
                disabledLabelColor = OnSurfaceVariant,
                disabledTrailingIconColor = OnSurfaceVariant,
                disabledPlaceholderColor = OnSurfaceVariant,
                disabledTextColor = OnSurface
            )

        )

        SimpleTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
            value = "",
            onValueChange = {

            },
            label = "${stringResource(SharedRes.strings.name)} *",
            placeholder = ""
        )

        SimpleTextField(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            value = "",
            onValueChange = {

            },
            label = "${stringResource(SharedRes.strings.description)}",
            placeholder = "",
            maxLines = 5,
            minLines = 3,
            singleLine = false,
        )
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp)
                    .border(
                        width = 1.dp,
                        color = OnSurfaceVariant,
                        shape = RoundedCornerShape(5)
                    )
                    .clip(shape = RoundedCornerShape(5))
            )
            {
                if (false) {
                    Column(modifier = Modifier.weight(4f)) {
                        Text(
                            modifier = Modifier.padding(top = 20.dp, start = 10.dp),
                            text = "${stringResource(SharedRes.strings.identification_number)}:",
                            fontSize = 12.sp
                        )
                        Text(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 20.dp),
                            text = "${stringResource(SharedRes.strings.name)}:",
                            fontSize = 12.sp
                        )
                    }
                    Box(
                        modifier = Modifier.weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {
                        IconButton(
                            modifier = Modifier.align(Alignment.Center),
                            onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.PersonAdd,
                                contentDescription = "select customer"
                            )
                        }
                    }
                } else {

                    Box(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp)) {
                        Button(
                            onClick = selectOnClick,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        {
                            Text(text = stringResource(SharedRes.strings.associate))
                        }
                    }
                }
            }
            Box(modifier = Modifier.height(20.dp).padding(start = 10.dp).background(Background)) {
                Text(
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    text = "${stringResource(SharedRes.strings.owner)} *",
                    color = OnSurfaceVariant
                )
            }
        }

    }

}