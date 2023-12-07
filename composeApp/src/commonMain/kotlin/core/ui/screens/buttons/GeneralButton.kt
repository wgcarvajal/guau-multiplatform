package core.ui.screens.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GeneralButton(modifier: Modifier, label: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled) {
        Text(text = label)
    }
}
