package core.ui.screens.snackbars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DecoupledSnackBar(
    snackBarHostState: SnackbarHostState,
    containerColor: Color,
    messageColor: Color,
    actionColor: Color
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackBarHostState,
            snackbar = {
                Snackbar(action = {
                    TextButton(onClick = {
                        snackBarHostState.currentSnackbarData?.dismiss()
                    }) {
                        Text(
                            text = snackBarHostState.currentSnackbarData?.visuals?.actionLabel
                                ?: "",
                            color = actionColor
                        )
                    }
                }, containerColor = containerColor) {
                    Text(
                        text = snackBarHostState.currentSnackbarData?.visuals?.message ?: "",
                        color = messageColor
                    )
                }
            }
        )
    }
}