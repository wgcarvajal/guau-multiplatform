package core.ui.screens.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TwoButtonDialog(
    show: Boolean,
    message: String,
    confirmButtonText: String,
    cancelButtonText: String,
    confirmButton: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            text = { Text(text = message, color = Color.Black) },
            dismissButton = {
                TextButton(onClick = {
                    onDismissRequest()
                }) {
                    Text(text = cancelButtonText, color = Color.Black)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    confirmButton()
                }) {
                    Text(text = confirmButtonText, color = Color.Black)
                }
            }
        )
    }
}