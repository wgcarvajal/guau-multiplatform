package home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.SharedRes
import core.ui.screens.itemlist.ItemHome
import dev.icerock.moko.resources.compose.stringResource
import ui.theme.NavyBlue
import ui.theme.Orange
import ui.theme.Purple

@Composable
fun HomeScreen(
    showNavigation: (Boolean) -> Unit,
    onShowTopBar: (Boolean) -> Unit,
    onShowExitCenter: (Boolean) -> Unit,
    onShowBottomBar: (Boolean) -> Unit,
    showFloatActionButton: (Boolean, () -> Unit) -> Unit,
    onSetTitle: (String) -> Unit,
    onAdmission:()->Unit
) {
    val title = stringResource(SharedRes.strings.home)
    SideEffect {
        showNavigation(false)
        onShowTopBar(true)
        onShowBottomBar(true)
        showFloatActionButton(false) {}
        onSetTitle(title)
        onShowExitCenter(true)
    }
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize()
            .padding(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
        columns = GridCells.Fixed(count = 2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            ItemHome(
                text = stringResource(SharedRes.strings.admission),
                backgroundColor = NavyBlue,
                imageResource = SharedRes.images.admit,
                onClick = onAdmission
            )
        }
        item {
            ItemHome(
                text = stringResource(SharedRes.strings.appointment),
                backgroundColor = Orange,
                imageResource = SharedRes.images.calendar,
                onClick = {}
            )
        }
        item {
            ItemHome(
                text = stringResource(SharedRes.strings.vaccines),
                backgroundColor = Purple,
                imageResource = SharedRes.images.vaccine,
                onClick = {}
            )
        }
    }
}