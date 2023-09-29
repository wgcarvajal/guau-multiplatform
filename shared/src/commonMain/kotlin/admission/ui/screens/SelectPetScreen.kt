package admission.ui.screens

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.SharedRes
import core.ui.constants.ScreenEnum
import core.ui.model.UiStructureProperties
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SelectPetScreen(uiStructureProperties: UiStructureProperties,onSelectAction:()->Unit) {
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(true)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.SelectPet)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.showActionNext(true)
        uiStructureProperties.onEnabledNextAction(false)
    }

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
}