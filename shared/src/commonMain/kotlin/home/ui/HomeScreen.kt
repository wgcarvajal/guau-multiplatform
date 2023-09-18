package home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.carpisoft.guau.SharedRes
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun HomeScreen(
    showNavigation: (Boolean) -> Unit,
    onShowTopBar: (Boolean) -> Unit,
    onShowExitCenter: (Boolean) -> Unit,
    onShowBottomBar: (Boolean) -> Unit,
    showFloatActionButton: (Boolean, () -> Unit) -> Unit,
    onSetTitle: (String) -> Unit
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
    LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(count = 2)) {
        item {

        }
    }
}