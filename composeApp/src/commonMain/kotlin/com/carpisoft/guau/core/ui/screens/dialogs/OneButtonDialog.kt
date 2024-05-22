package com.carpisoft.guau.core.ui.screens.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.ok
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
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
                    Text(text = stringResource(Res.string.ok), color = Color.Black)
                }
            }
        )
    }
}
