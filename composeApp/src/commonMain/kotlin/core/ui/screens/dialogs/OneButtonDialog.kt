package core.ui.screens.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.carpisoft.guau.SharedRes
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun OneButtonDialog(
    show: Boolean,
    message: String,
    onDismissRequest: () -> Unit,
) {
    if (show) {
        AlertDialog(onDismissRequest = onDismissRequest,
            text = { Text(text = message, color = Color.Black) },
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(SharedRes.strings.ok), color = Color.Black)
                }
            }
        )
    }
}
