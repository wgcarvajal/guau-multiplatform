package core.ui.screens.datepicker

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.carpisoft.guau.SharedRes
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDatePickerDialog(
    datePickerState: DatePickerState,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    dateValidator: (Long) -> Boolean = { true }
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
                Text(text = stringResource(SharedRes.strings.ok))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(SharedRes.strings.cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            dateValidator = dateValidator,
            showModeToggle = false,
            title = null,
            headline = null
        )
    }
}
