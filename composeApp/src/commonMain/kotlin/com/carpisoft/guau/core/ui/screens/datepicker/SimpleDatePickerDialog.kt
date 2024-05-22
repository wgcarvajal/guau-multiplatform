package com.carpisoft.guau.core.ui.screens.datepicker

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.ok
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun SimpleDatePickerDialog(
    datePickerState: DatePickerState,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val selectedDate = datePickerState.selectedDateMillis?.let {
        it.toString()
    } ?: ""
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = stringResource(Res.string.ok))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(Res.string.cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            title = null,
            headline = null
        )
    }
}
