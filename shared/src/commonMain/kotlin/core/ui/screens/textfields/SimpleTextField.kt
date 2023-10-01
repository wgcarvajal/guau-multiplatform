package core.ui.screens.textfields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun SimpleTextField(
    modifier: Modifier,
    value: String,
    placeholder: String,
    label: String,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
    passwordVisibility: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        placeholder = {
            Text(text = placeholder)
        },
        label = { Text(text = label) },
        onValueChange = onValueChange,
        modifier = modifier,
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        maxLines = maxLines,
        minLines = minLines,
        singleLine = singleLine,
        colors = colors
    )
}

@Composable
fun SimpleTextFieldPassword(
    modifier: Modifier,
    value: String,
    placeholder: String,
    label: String,
    onValueChange: (String) -> Unit,
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    SimpleTextField(
        modifier = modifier,
        value = value,
        placeholder = placeholder,
        label = label,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        ),
        onValueChange = onValueChange,
        passwordVisibility = passwordVisibility,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Key,
                contentDescription = "password icon"
            )
        },
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "show password")
            }
        }

    )
}
